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
    RepositorioServicio repositorioServicio;

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioTicket repositorioTicket;

    @Autowired
    RepositorioClienteProducto repositorioClienteProducto;


    @GetMapping("/compras")
    private Set<CompraDTO> compra(){
        Set<CompraDTO> comprasDTO = new HashSet<>(repositorioCompra.findAll()).stream().map(CompraDTO::new).collect(Collectors.toSet());
        return comprasDTO;
    }

    //comentario

    @PostMapping("/compra")
    private ResponseEntity<Object> agregarCompraCarrito(
            Authentication authentication,
            @RequestBody Set<CarritoProductoDTO> carritoProductoDTOS) {

        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());

        if(carritoProductoDTOS.isEmpty()){
            return new ResponseEntity<>("No ha comprado ningún producto", HttpStatus.FORBIDDEN);
        }

        // Validar que hay stock suficiente ANTES de comprar
        for(CarritoProductoDTO item : carritoProductoDTOS){
            Producto producto = repositorioProducto.findById(item.getIdProducto()).orElse(null);
            if(producto == null){
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            }
            if(producto.getStock() < item.getCantidad()){
                return new ResponseEntity<>("Stock insuficiente para: " + producto.getNombre(), HttpStatus.FORBIDDEN);
            }
        }
        // Calcular el total de la compra primero
        double totalCompra = 0;
        for(CarritoProductoDTO item : carritoProductoDTOS){
            Producto producto = repositorioProducto.findById(item.getIdProducto()).orElse(null);
            if(producto == null){
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            }
            totalCompra += producto.getValor() * item.getCantidad();
        }

        // Verificar si tiene saldo suficiente
        if(cliente.getSaldo() < totalCompra){
            return new ResponseEntity<>("Saldo insuficiente. Tu saldo: $" + cliente.getSaldo() +
                    " - Total compra: $" + totalCompra, HttpStatus.FORBIDDEN);
        }

        // Crear el ticket
        Ticket ticket = new Ticket();
        repositorioTicket.save(ticket);
        double totalTicket = 0;

        // Procesar cada producto
        for(CarritoProductoDTO item : carritoProductoDTOS){
            Producto producto = repositorioProducto.findById(item.getIdProducto()).orElse(null);

            // Crear la compra
            Compra compra = new Compra(cliente, producto.getNombre(),
                    producto.getValor() * item.getCantidad(),
                    item.getCantidad(), ticket);

            // Crear la relación cliente-producto
            ClienteProducto clienteProducto = new ClienteProducto(cliente, producto, compra);

            // DESCONTAR EL STOCK
            producto.setStock(producto.getStock() - item.getCantidad());

            // Sumar al total
            totalTicket += compra.getTotalCompraProducto();

            // Guardar todo
            repositorioProducto.save(producto);
            repositorioCompra.save(compra);
            repositorioClienteProducto.save(clienteProducto);
        }

        ticket.setTotalCompraValor(totalTicket);
        repositorioTicket.save(ticket);
        // Descontar el saldo
        cliente.setSaldo(cliente.getSaldo() - totalTicket);
        repositorioCliente.save(cliente);

        return new ResponseEntity<>("Compra realizada con éxito", HttpStatus.CREATED);
    }

    @GetMapping("/compras/historial")
    public ResponseEntity<Object> verHistorial(Authentication authentication){
        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());

        Set<Compra> compras = repositorioCompra.findByCliente(cliente);

        Set<CompraDTO> comprasDTO = compras.stream()
                .map(CompraDTO::new)
                .collect(Collectors.toSet());

        return new ResponseEntity<>(comprasDTO, HttpStatus.OK);
    }



}
