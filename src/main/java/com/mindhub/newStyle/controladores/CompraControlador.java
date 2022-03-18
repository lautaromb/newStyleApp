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

    @GetMapping("/compras/{id}")
    private Set<CompraDTO> comprasByClient(@PathVariable Long id, Authentication authentication){
        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());
        Set<CompraDTO> comprasDTO = new HashSet<>(repositorioCompra.findAll()).stream().map(CompraDTO::new).collect(Collectors.toSet());

        return comprasDTO;
    }


    @GetMapping("/ticket/{id}")
    private Ticket getTicketByClienteId(@PathVariable Long id, Authentication authentication){
        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());
        Ticket ticket = repositorioTicket.findByClienteId(id);

        return ticket;

    }

    //comentar

    @PostMapping("/compra")
    private ResponseEntity<Object> agregarCompraCarrito(Authentication authentication,
                                                        @RequestBody Set<CarritoProductoDTO> carritoProductoDTOS ){

        //

        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());
//        Producto producto = repositorioProducto.findById(carritoClienteDTO.getIdProducto()).orElse(null);


        if(carritoProductoDTOS.size() == 0 ){
            return new ResponseEntity<>("No ha comprado ningun prodcuto ó servicio", HttpStatus.FORBIDDEN);
        }



//        Set<Producto> productos = carritoProductoDTOS.stream().map()

        Ticket ticketProducto = new Ticket();

        carritoProductoDTOS.forEach(productoEnCarrito -> {
            Producto productoIterado = repositorioProducto.findById(productoEnCarrito.getId()).orElse(null);
            Compra compra = new Compra(cliente.getPrimerNombre() , TypeCompra.EFECTIVO , cliente , productoEnCarrito.getNombre(), productoEnCarrito.getPrecio() * productoEnCarrito.getStock() , productoEnCarrito.getStock(), ticketProducto);
            ClienteProducto clienteProducto = new ClienteProducto(cliente, productoIterado, compra);

            ticketProducto.setTotalCompraValor(compra.getTotalCompraProducto() + ticketProducto.getTotalCompraValor());
            ticketProducto.setCliente(cliente);

            repositorioTicket.save(ticketProducto);
            repositorioCompra.save(compra);
            repositorioClienteProducto.save(clienteProducto);
        });



        return new ResponseEntity<>("Se compro con exito", HttpStatus.CREATED);

    }







}
