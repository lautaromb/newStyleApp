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

        // verificacion de email correcta
        if(!email.contains("@gmail.com") || !email.contains("@outloock.com") || !email.contains("@hotmail.com") || !email.contains("@mindhub.com")){
            return new ResponseEntity<>("El email no es correcto, no es uno de los asociados", HttpStatus.FORBIDDEN);
        }



        Cliente cliente = new Cliente(primerNombre, apellido, email, passwordEncoder.encode(password) , numeroTel);
        clienteServicio.saveCliente(cliente);

        return new ResponseEntity<>("Cliente creado", HttpStatus.CREATED);

    }


}
