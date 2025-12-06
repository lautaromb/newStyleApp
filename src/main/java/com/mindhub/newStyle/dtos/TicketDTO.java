package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Ticket;

import java.util.Set;
import java.util.stream.Collectors;

public class TicketDTO {
    private Long id;
    private double totalCompraValor;
    private boolean entregado;
    private Set<CompraDTO> compras;

    public TicketDTO() {
    }

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.totalCompraValor = ticket.getTotalCompraValor();
        this.entregado = ticket.isEntregado();
        this.compras = ticket.getCompras().stream()
                .map(CompraDTO::new)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public double getTotalCompraValor() {
        return totalCompraValor;
    }

    public void setTotalCompraValor(double totalCompraValor) {
        this.totalCompraValor = totalCompraValor;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public Set<CompraDTO> getCompras() {
        return compras;
    }

    public void setCompras(Set<CompraDTO> compras) {
        this.compras = compras;
    }
}