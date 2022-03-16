package com.mindhub.newStyle.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.newStyle.modelos.*;

import java.util.Set;
import java.util.stream.Collectors;

public class CompraDTO {
    private Long id;
    private TypeCompra typeCompra;
    private String nombreCliente;
    private double totalCompra;
    private String nombreProducto;
    private int stock;
    private Cliente cliente;
    private Set<ClienteProductoDTO> clienteProductoDTOS;
    //private ClienteServicio clienteServicio;
    private Ticket ticket;


    public CompraDTO() {
    }

    public CompraDTO(Compra compra) {
        this.id = compra.getId();
        this.nombreCliente = compra.getCliente().getPrimerNombre();
        this.typeCompra = compra.getTypeCompra();
        this.nombreProducto = compra.getNombreProducto();
        this.stock = compra.getStock();
        this.totalCompra = compra.getTotalCompraProducto();
        this.clienteProductoDTOS = compra.getClienteProductoSet().stream().map(ClienteProductoDTO::new).collect(Collectors.toSet());
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

    @JsonIgnore
    public String getNombreCliente() {
        return nombreCliente;
    }
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
    public Set<ClienteProductoDTO> getClienteProductoDTOS() {
        return clienteProductoDTOS;
    }

    public void setClienteProductoDTOS(Set<ClienteProductoDTO> clienteProductoDTOS) {
        this.clienteProductoDTOS = clienteProductoDTOS;
    }





//    public ClienteServicio getClienteServicio() {
//        return clienteServicio;
//    }
//
//    public void setClienteServicio(ClienteServicio clienteServicio) {
//        this.clienteServicio = clienteServicio;
//    }

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
