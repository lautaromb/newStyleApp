package com.mindhub.newStyle.repositorios;

import com.mindhub.newStyle.modelos.ClienteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestResource
public interface RepositorioClienteProducto extends JpaRepository<ClienteProducto, Long> {
}
