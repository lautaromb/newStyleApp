package com.mindhub.newStyle.repositorios;

import com.mindhub.newStyle.modelos.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioTicket extends JpaRepository<Ticket, Long> {
    Ticket findByClienteId(long id);
}
