package com.mindhub.newStyle.controladores;


import com.mindhub.newStyle.dtos.ServicioDTO;
import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.Servicio;
import com.mindhub.newStyle.repositorios.RepositorioNegocio;
import com.mindhub.newStyle.repositorios.RepositorioServicio;
import com.mindhub.newStyle.servicios.implementaciones.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ServicioControlador {

    @Autowired
    ClienteServicio clienteServicio;

    @Autowired
    RepositorioServicio repositorioServicio;

    @Autowired
    RepositorioNegocio repositorioNegocio;

    @GetMapping("/servicio")
    public Set<ServicioDTO> getServiciosDTO () {
        Set<ServicioDTO> servicioDTOS = repositorioServicio.findAll().stream().map(servicio -> new ServicioDTO(servicio)).collect(Collectors.toSet());
        return servicioDTOS;
    }

    @PostMapping("/servicio")
    public ResponseEntity<Object> createService(@RequestParam String nombre, @RequestParam double valor,
                                                @RequestParam String imagenPrincipal, @RequestParam String imagenDesc1, @RequestParam String imagenDesc2, @RequestParam String descripcion){


        if (nombre.isEmpty() || valor <= 0 || imagenPrincipal.isEmpty() || imagenDesc1.isEmpty() || imagenDesc2.isEmpty() || descripcion.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }


          Servicio servicio = new Servicio(nombre, valor, imagenPrincipal, imagenDesc1, imagenDesc2, descripcion);


        repositorioServicio.save(servicio);



        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    };
}
