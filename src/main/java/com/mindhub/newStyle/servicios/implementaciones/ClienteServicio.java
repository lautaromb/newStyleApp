package com.mindhub.newStyle.servicios.implementaciones;

import com.mindhub.newStyle.dtos.ClienteDTO;
import com.mindhub.newStyle.modelos.Cliente;

import java.util.List;


public interface ClienteServicio {
    public List<ClienteDTO> getClientesDTO();
    public Cliente saveCliente (Cliente cliente);
    public ClienteDTO findClienteByID (Long id);
    public Cliente findClienteByEmail (String email);
//comentario
}
