package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.CarritoProductoDTO;
import com.mindhub.newStyle.dtos.CarritoServicioDTO;
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

        // Crear el ticket
        Ticket ticket = new Ticket();
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

        return new ResponseEntity<>("Compra realizada con éxito", HttpStatus.CREATED);
    }




//    @PostMapping("/compra")
//    private ResponseEntity<Object> agregarCompraCarrito(Authentication authentication,
//                                                @RequestParam long producto_id, @RequestParam int producto_cantidad, @RequestParam long ticketOrdenCompra ){
//
//
//        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());
//        Producto producto = repositorioProducto.findById(producto_id).orElse(null);
//        Ticket ticket = repositorioTicket.findById(ticketOrdenCompra).orElse(null);
//
//        if(ticket == null){
//            ticket = new Ticket();
//        }
//
//
//        Compra compra = new Compra(cliente.getPrimerNombre() , cliente ,producto.getNombre(), producto.getValor() + producto_cantidad, producto_cantidad, ticket);
//        ClienteProducto clienteProducto = new ClienteProducto(cliente, producto, compra);
//
//        repositorioCompra.save(compra);
//        repositorioTicket.save(ticket);
//        repositorioClienteProducto.save(clienteProducto);
//
//
//        /*Que se genere el ticket cuando le dan a comprar*/
//
//        return new ResponseEntity<>("Se creo con exito", HttpStatus.CREATED);
//
//    }



}
