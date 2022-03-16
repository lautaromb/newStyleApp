package com.mindhub.newStyle.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.newStyle.modelos.*;

public class CompraDTO {
    private Long id;
    private TypeCompra typeCompra;
    private String nombreCliente;
    private double totalCompra;
    private String nombreProducto;
    private int stock;
    private Cliente cliente;
    private ClienteProducto clienteProducto;
    private ClienteServicio clienteServicio;
    private Ticket ticket;


    public CompraDTO() {
    }

    public CompraDTO(Compra compra) {
        this.id = compra.getId();
        this.nombreCliente = compra.getCliente().getPrimerNombre();
        this.typeCompra = compra.getTypeCompra();
        this.nombreProducto = compra.getClienteProducto().getProducto().getNombre();
        this.stock = compra.getStock();
        this.totalCompra = compra.getTotalCompraProducto();
        this.clienteProducto = compra.getClienteProducto();
        this.ticket = compra.getTicket();
    }

    public Long getId() {
        return id;
    }


    public TypeCompra getTypeCompra() {
        return typeCompra;
    }

    public void setTypeCompra(TypeCompra typeCompra) {
        this.typeCompra = typeCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    @JsonIgnore
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @JsonIgnore
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    @JsonIgnore
    public ClienteProducto getClienteProducto() {
        return clienteProducto;
    }

    public void setClienteProducto(ClienteProducto clienteProducto) {
        this.clienteProducto = clienteProducto;
    }

    public ClienteServicio getClienteServicio() {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
