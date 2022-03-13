package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.ClienteDTO;
import com.mindhub.newStyle.repositorios.RepositorioCliente;
import com.mindhub.newStyle.repositorios.RepositorioProducto;
import com.mindhub.newStyle.repositorios.RepositorioServicio;
import com.mindhub.newStyle.servicios.implementaciones.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteControlador {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioServicio repositorioServicio;

    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping("/cliente")
    public List<ClienteDTO> getClientDTOByEmail(Authentication authentication){
        return clienteServicio.getClientesDTO();
    }



}
