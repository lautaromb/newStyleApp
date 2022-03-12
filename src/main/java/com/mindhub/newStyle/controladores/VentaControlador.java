package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.repositorios.RepositorioCliente;
import com.mindhub.newStyle.repositorios.RepositorioProducto;
import com.mindhub.newStyle.repositorios.RepositorioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VentaControlador {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioServicio repositorioServicio;


    //@GetMapping()

}
