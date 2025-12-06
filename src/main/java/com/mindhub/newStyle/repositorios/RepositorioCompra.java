package com.mindhub.newStyle.repositorios;

import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface RepositorioCompra extends JpaRepository<Compra, Long> {
    Set<Compra> findByCliente(Cliente cliente);
}
