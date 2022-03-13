package com.mindhub.newStyle.servicios;

import com.mindhub.newStyle.dtos.ClienteDTO;
import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.repositorios.RepositorioCliente;
import com.mindhub.newStyle.servicios.implementaciones.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteImplementacion implements ClienteServicio {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Override
    public List<ClienteDTO> getClientesDTO() {
        return repositorioCliente.findAll().stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return repositorioCliente.save(cliente);
    }

    @Override
    public ClienteDTO findClienteByID(Long id) {
        //return repositorioCliente.findById(id).map(ClienteDTO::new).orElse(null);
        return repositorioCliente.findById(id).map(cliente -> new ClienteDTO(cliente)).orElse(null);
    }

    @Override
    public Cliente findClienteByEmail(String email) {

        return repositorioCliente.findByEmail(email);
    }
}
