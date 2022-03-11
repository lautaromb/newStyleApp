package com.mindhub.newStyle.repositorios;

import com.mindhub.newStyle.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioProducto extends JpaRepository<Producto, Long> {

}
