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
    private ResponseEntity<Object> agregarCompraCarrito(Authentication authentication,
                                                        @RequestBody Set<CarritoProductoDTO> carritoProductoDTOS,
                                                        @RequestBody Set<CarritoServicioDTO> carritoServicioDTOS){

        //

        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());
//        Producto producto = repositorioProducto.findById(carritoClienteDTO.getIdProducto()).orElse(null);


        if(carritoProductoDTOS.size() == 0 || carritoServicioDTOS.size() == 0){
            return new ResponseEntity<>("No ha comprado ningun prodcuto ó servicio", HttpStatus.FORBIDDEN);
        }



//        Set<Producto> productos = carritoProductoDTOS.stream().map()

        Ticket ticketProducto = new Ticket();
        carritoProductoDTOS.forEach(productoEnCarrito -> {
            Producto productoIterado = repositorioProducto.findById(productoEnCarrito.getIdProducto()).orElse(null);
            Compra compra = new Compra(cliente, productoEnCarrito.getNombre(), productoEnCarrito.getValor() * productoEnCarrito.getCantidad() , productoEnCarrito.getCantidad(), ticketProducto);
            ClienteProducto clienteProducto = new ClienteProducto(cliente, productoIterado, compra);

            ticketProducto.setTotalCompraValor(compra.getTotalCompraProducto() + ticketProducto.getTotalCompraValor());

            repositorioTicket.save(ticketProducto);
            repositorioCompra.save(compra);
            repositorioClienteProducto.save(clienteProducto);
        });



//        Ticket ticketServicio = new Ticket();
//        carritoServicioDTOS.forEach(servicioEnCarrito -> {
//            Servicio servicioIterado = repositorioServicio.findById(servicioEnCarrito.getIdProducto()).orElse(null);
//            Compra compra = new Compra(cliente, servicioEnCarrito.getNombre(), servicioEnCarrito.getValor() * servicioEnCarrito.getCantidad(), servicioEnCarrito.getCantidad(), ticketServicio);
//            ClienteServicio =
//        });






       // Compra compra = new Compra(cliente.getPrimerNombre() , cliente ,producto.getNombre(), producto.getValor() + producto_cantidad, producto_cantidad, ticket);
       // ClienteProducto clienteProducto = new ClienteProducto(cliente, producto, compra);

//        repositorioCompra.save(compra);
//        repositorioTicket.save(ticket);
       // repositorioClienteProducto.save(clienteProducto);


        /*Que se genere el ticket cuando le dan a comprar*/

        return new ResponseEntity<>("Se compro con exito", HttpStatus.CREATED);

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
