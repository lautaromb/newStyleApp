package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.ProductoDTO;
import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.Producto;
import com.mindhub.newStyle.modelos.Servicio;
import com.mindhub.newStyle.repositorios.RepositorioNegocio;
import com.mindhub.newStyle.repositorios.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductoControlador {

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioNegocio repositorioNegocio;

    @GetMapping("/producto")
    public Set<ProductoDTO> getProductoDTOS(){
    Set<ProductoDTO> productoDTOS = repositorioProducto.findAll().stream().map(ProductoDTO::new).collect(Collectors.toSet());
    return productoDTOS;
    }

    //Fermin

    @PostMapping("/producto")
    public ResponseEntity<Object> createService(@RequestParam String nombre, @RequestParam double valor,
                                                @RequestParam String imagenProducto, @RequestParam String imagenCard,
                                                @RequestParam int stock ,@RequestParam String descripcion){


        Negocio negocio = repositorioNegocio.findByEmail("newStyle@gmail.com");

        if (nombre.isEmpty() || valor <= 0 || imagenProducto.isEmpty() || imagenCard.isEmpty() || descripcion.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }


        Producto producto = new Producto(nombre, valor, imagenProducto, imagenCard, stock,descripcion, negocio);


        repositorioProducto.save(producto);



        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    };


}
