package com.mindhub.newStyle.repositorios;

import com.mindhub.newStyle.modelos.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioServicio extends JpaRepository<Servicio, Long> {
}
