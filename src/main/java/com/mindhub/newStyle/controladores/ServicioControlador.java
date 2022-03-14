package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.ServicioDTO;
import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.Servicio;
import com.mindhub.newStyle.repositorios.RepositorioCliente;
import com.mindhub.newStyle.repositorios.RepositorioServicio;
import com.mindhub.newStyle.servicios.implementaciones.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ServicioControlador {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioServicio repositorioServicio;

    @GetMapping("/servicio")
    public Set<ServicioDTO> getServiciosDTO () {
      Set<ServicioDTO> servicioDTOS = repositorioServicio.findAll().stream().map(servicio -> new ServicioDTO(servicio)).collect(Collectors.toSet());
        return servicioDTOS;
    }

    @PostMapping("/servicio")
    public ResponseEntity<Object> createService(@RequestParam String nombre,@RequestParam double valor,
                                                @RequestParam String imagenPrincipal, @RequestParam String descripcion){
        if (nombre.isEmpty() || valor <= 0 || imagenPrincipal.isEmpty() || descripcion.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Se creo con exito",HttpStatus.CREATED);
    }
}
