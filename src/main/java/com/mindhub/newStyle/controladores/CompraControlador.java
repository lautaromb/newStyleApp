package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.CarritoProductoDTO;
import com.mindhub.newStyle.dtos.CompraDTO;
import com.mindhub.newStyle.modelos.*;
import com.mindhub.newStyle.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CompraControlador {

    @Autowired
    RepositorioCompra repositorioCompra;

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioTicket repositorioTicket;

    @Autowired
    RepositorioClienteProducto repositorioClienteProducto;


    @GetMapping("/compras")
    private Set<CompraDTO> compra() {
        return repositorioCompra.findAll()
                .stream()
                .map(CompraDTO::new)
                .collect(Collectors.toSet());
    }


    @PostMapping("/compra")
    private ResponseEntity<Object> agregarCompraCarrito(
            Authentication authentication,
            @RequestBody Set<CarritoProductoDTO> carritoDTOs) {

        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());

        if (cliente == null) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.UNAUTHORIZED);
        }

        if (carritoDTOs.isEmpty()) {
            return new ResponseEntity<>("El carrito está vacío", HttpStatus.BAD_REQUEST);
        }


        // Validar stock disponible
        for (CarritoProductoDTO item : carritoDTOs) {
            Producto producto = repositorioProducto.findById(item.getIdProducto()).orElse(null);

            if (producto == null) {
                return new ResponseEntity<>("Producto no encontrado: " + item.getIdProducto(), HttpStatus.NOT_FOUND);
            }

            if (producto.getStock() < item.getCantidad()) {
                return new ResponseEntity<>("Stock insuficiente para: " + producto.getNombre(), HttpStatus.FORBIDDEN);
            }
        }


        // Calcular total de la compra
        double totalCompra = carritoDTOs.stream()
                .mapToDouble(item -> {
                    Producto p = repositorioProducto.findById(item.getIdProducto()).orElse(null);
                    return p.getValor() * item.getCantidad();
                })
                .sum();


        // Validar saldo
        if (cliente.getSaldo() < totalCompra) {
            return new ResponseEntity<>(
                    "Saldo insuficiente. Tu saldo: $" + cliente.getSaldo() +
                            " - Total compra: $" + totalCompra,
                    HttpStatus.FORBIDDEN
            );
        }


        // Crear ticket
        Ticket ticket = new Ticket(cliente);
        repositorioTicket.save(ticket);


        // Crear compra vacía
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setFecha(LocalDateTime.now());
        compra.setTicket(ticket);
        compra.setTotal(0); // Se completa más adelante
        repositorioCompra.save(compra);

        double totalFinal = 0;


        // Procesar cada producto del carrito
        for (CarritoProductoDTO item : carritoDTOs) {

            Producto producto = repositorioProducto.findById(item.getIdProducto()).orElse(null);

            ClienteProducto cp = new ClienteProducto();
            cp.setCompra(compra);
            cp.setProducto(producto);
            cp.setCantidad(item.getCantidad());
            cp.setPrecioUnitario(producto.getValor());

            totalFinal += cp.getSubtotal();

            producto.setStock(producto.getStock() - item.getCantidad());
            repositorioProducto.save(producto);

            repositorioClienteProducto.save(cp);

            compra.getClienteProductos().add(cp);
        }


        // Actualizar total de la compra
        compra.setTotal(totalFinal);
        repositorioCompra.save(compra);


        // Descontar saldo del cliente
        cliente.setSaldo(cliente.getSaldo() - totalFinal);
        repositorioCliente.save(cliente);


        // Actualizar ticket
        ticket.setTotalCompraValor(totalFinal);
        repositorioTicket.save(ticket);

        return new ResponseEntity<>("Compra realizada con éxito", HttpStatus.CREATED);
    }


    @GetMapping("/compras/historial")
    public ResponseEntity<Object> verHistorial(Authentication authentication) {

        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());

        Set<CompraDTO> dtos = repositorioCompra.findByCliente(cliente)
                .stream()
                .map(CompraDTO::new)
                .collect(Collectors.toSet());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}

