package com.mindhub.newStyle.repositorios;


import com.mindhub.newStyle.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioCliente extends JpaRepository<Cliente, Long> {

}
