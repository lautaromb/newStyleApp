package com.mindhub.newStyle.controladores;

import ch.qos.logback.core.net.server.Client;
import com.mindhub.newStyle.dtos.ClienteDTO;
import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.repositorios.RepositorioCliente;
import com.mindhub.newStyle.repositorios.RepositorioProducto;
import com.mindhub.newStyle.repositorios.RepositorioServicio;
import com.mindhub.newStyle.servicios.implementaciones.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteControlador {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioServicio repositorioServicio;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping("/cliente")
    public List<ClienteDTO> getClientDTOByEmail(Authentication authentication){
        return clienteServicio.getClientesDTO();
    }

    @GetMapping("/cliente/{id}")
    public ClienteDTO getClientDTO(@PathVariable Long id){
        return clienteServicio.findClienteByID(id);
    }

    @GetMapping("/cliente/current")
    public ClienteDTO getClienteByEmail(Authentication authentication){
        ClienteDTO clienteDTO = new ClienteDTO(clienteServicio.findClienteByEmail(authentication.getName()));
        return clienteDTO;
    }

    @PostMapping("/clientes")
    public ResponseEntity<Object> registrar (
            @RequestParam String primerNombre,@RequestParam String apellido, @RequestParam String email,
            @RequestParam String password, @RequestParam String numeroTel){




        if(primerNombre.isEmpty() || apellido.isEmpty() || email.isEmpty() ||password.isEmpty() || numeroTel.isEmpty()){
            return new ResponseEntity<>("Faltan datos", HttpStatus.FORBIDDEN);
        }

        if(clienteServicio.findClienteByEmail(email) != null){
            return new ResponseEntity<>("Email ya registrado", HttpStatus.FORBIDDEN);
        }


        Cliente cliente = new Cliente(primerNombre, apellido, email, passwordEncoder.encode(password) , numeroTel);
        clienteServicio.saveCliente(cliente);

        return new ResponseEntity<>("Cliente creado", HttpStatus.CREATED);

    }

    @GetMapping("/cliente/saldo")
    public ResponseEntity<Object> verSaldo(Authentication authentication) {
        Cliente cliente = clienteServicio.findClienteByEmail(authentication.getName());

        return new ResponseEntity<>(cliente.getSaldo(), HttpStatus.OK);
    }

    @PostMapping("/cliente/saldo/agregar")
    public ResponseEntity<Object> agregarSaldo(
            Authentication authentication,
            @RequestParam double monto) {

        // Validar que el monto sea positivo
        if (monto <= 0) {
            return new ResponseEntity<>("El monto debe ser mayor a 0", HttpStatus.FORBIDDEN);
        }

        // Buscar el cliente
        Cliente cliente = clienteServicio.findClienteByEmail(authentication.getName());

        // Agregar el dinero
        cliente.setSaldo(cliente.getSaldo() + monto);

        // Guardar cambios
        clienteServicio.saveCliente(cliente);

        return new ResponseEntity<>("Saldo agregado. Nuevo saldo: $" + cliente.getSaldo(), HttpStatus.OK);
    }

    @PostMapping("/cliente/saldo/transferir")
    public ResponseEntity<Object> transferirSaldo(
            Authentication authentication,
            @RequestParam String emailDestino,
            @RequestParam double monto) {

        // Validar que el monto sea positivo
        if (monto <= 0) {
            return new ResponseEntity<>("El monto debe ser mayor a 0", HttpStatus.FORBIDDEN);
        }

        // Buscar cliente que transfiere (origen)
        Cliente clienteOrigen = clienteServicio.findClienteByEmail(authentication.getName());

        // Validar que no se transfiera a sí mismo
        if (clienteOrigen.getEmail().equals(emailDestino)) {
            return new ResponseEntity<>("No puedes transferir a tu propia cuenta", HttpStatus.FORBIDDEN);
        }

        // Buscar cliente que recibe (destino)
        Cliente clienteDestino = clienteServicio.findClienteByEmail(emailDestino);

        if (clienteDestino == null) {
            return new ResponseEntity<>("El cliente destino no existe", HttpStatus.NOT_FOUND);
        }

        // Validar que tenga saldo suficiente
        if (clienteOrigen.getSaldo() < monto) {
            return new ResponseEntity<>("Saldo insuficiente. Tu saldo: $" + clienteOrigen.getSaldo(),
                    HttpStatus.FORBIDDEN);
        }

        // Realizar la transferencia
        clienteOrigen.setSaldo(clienteOrigen.getSaldo() - monto);
        clienteDestino.setSaldo(clienteDestino.getSaldo() + monto);

        // Guardar cambios
        clienteServicio.saveCliente(clienteOrigen);
        clienteServicio.saveCliente(clienteDestino);

        return new ResponseEntity<>("Transferencia exitosa. Nuevo saldo: $" + clienteOrigen.getSaldo(),
                HttpStatus.OK);
    }


    @PostMapping("/cliente/saldo/remover")
    public ResponseEntity<Object> removerSaldo(
            Authentication authentication,
            @RequestParam double monto) {

        // Validar que el monto sea positivo
        if (monto <= 0) {
            return new ResponseEntity<>("El monto debe ser mayor a 0", HttpStatus.FORBIDDEN);
        }

        // Buscar el cliente
        Cliente cliente = clienteServicio.findClienteByEmail(authentication.getName());

        // Validar que tenga saldo suficiente
        if (cliente.getSaldo() < monto) {
            return new ResponseEntity<>("Saldo insuficiente. Tu saldo: $" + cliente.getSaldo(),
                    HttpStatus.FORBIDDEN);
        }

        // Remover el dinero
        cliente.setSaldo(cliente.getSaldo() - monto);

        // Guardar cambios
        clienteServicio.saveCliente(cliente);

        return new ResponseEntity<>("Saldo removido. Nuevo saldo: $" + cliente.getSaldo(),
                HttpStatus.OK);
    }

}
