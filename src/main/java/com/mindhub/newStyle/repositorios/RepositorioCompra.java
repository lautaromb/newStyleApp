package com.mindhub.newStyle.repositorios;

import com.mindhub.newStyle.modelos.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioCompra extends JpaRepository<Compra, Long> {
}
