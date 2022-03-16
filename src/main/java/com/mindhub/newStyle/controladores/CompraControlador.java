package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.CompraDTO;
import com.mindhub.newStyle.modelos.*;
import com.mindhub.newStyle.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
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


    ;

    @GetMapping("/compras")
    private Set<CompraDTO> compra(){
        Set<CompraDTO> comprasDTO = new HashSet<>(repositorioCompra.findAll()).stream().map(CompraDTO::new).collect(Collectors.toSet());
        return comprasDTO;
    }

    @PostMapping("/compras")
    private ResponseEntity<Object> crearCompra(Authentication authentication, @RequestParam TypeCompra typeCompra,
                                               @RequestParam long productoID, @RequestParam int stock ){


        Ticket ticket = new Ticket();
        Cliente cliente = repositorioCliente.findByEmail(authentication.getName());
        Producto producto = repositorioProducto.findById(productoID).orElse(null);
        ClienteProducto clienteProducto = new ClienteProducto(cliente, producto);

        if(producto == null){
            return new ResponseEntity<>("Porducto no existe", HttpStatus.FORBIDDEN);
        }



        if(typeCompra.equals(TypeCompra.TARJETA)){

            Compra compra = new Compra(cliente.getPrimerNombre(),cliente, typeCompra, producto.getNombre(), producto.getValor(), stock,clienteProducto);
            return new ResponseEntity<>("Se creo con exito", HttpStatus.CREATED);
        }

        Compra compra = new Compra(cliente.getPrimerNombre(), cliente,typeCompra,  producto.getNombre(), producto.getValor(), stock,clienteProducto );
        repositorioClienteProducto.save(clienteProducto);
        repositorioCompra.save(compra);


        return new ResponseEntity<>("Se creo con exito", HttpStatus.CREATED);

    }


}
