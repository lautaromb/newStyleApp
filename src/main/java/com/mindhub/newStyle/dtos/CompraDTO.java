package com.mindhub.newStyle.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.newStyle.modelos.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CompraDTO {

    private Long id;
    private LocalDateTime fecha;
    private double total;
    private Set<ClienteProductoDTO> productos;
    private Long ticketId;

    public CompraDTO(Compra compra) {
        this.id = compra.getId();
        this.fecha = compra.getFecha();
        this.total = compra.getTotal();
        this.ticketId = compra.getTicket() != null ? compra.getTicket().getId() : null;
        this.productos = compra.getClienteProductos()
                .stream()
                .map(ClienteProductoDTO::new)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Set<ClienteProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(Set<ClienteProductoDTO> productos) {
        this.productos = productos;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
