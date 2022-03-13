package com.mindhub.newStyle.servicios.implementaciones;

import com.mindhub.newStyle.dtos.ClienteDTO;
import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteImplementacion implements ClienteServicio{

    @Autowired
    RepositorioCliente repositorioCliente;

    @Override
    public List<ClienteDTO> getClientesDTO() {
        return repositorioCliente.findAll().stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return null;
    }

    @Override
    public ClienteDTO findClienteByID(Long id) {
        return null;
    }

    @Override
    public Cliente findClienteByEmail(String email) {
        return null;
    }
}
