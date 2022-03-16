package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private TypeCompra typeCompra;
    private String nombreCliente;
    private String nombreProducto;
    private double totalCompraProducto;
    private int stock;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_producto")
    private ClienteProducto clienteProducto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_servicio")
    private ClienteServicio clienteServicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket")
    private Ticket ticket;


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "producto_id")
//    private Producto producto;

    public Compra() {
    }

    public Compra(String nombreCliente, Cliente cliente, TypeCompra typeCompra, String nombreProducto, double totalCompraProducto, int stock, ClienteProducto clienteProducto) {

        this.nombreCliente = nombreCliente;
        this.cliente = cliente;
        this.typeCompra = typeCompra;
        this.nombreProducto = nombreProducto;
        this.totalCompraProducto = totalCompraProducto;
        this.stock = stock;
        this.clienteProducto = clienteProducto;
    }

//    public Compra(TypeCompra typeCompra, double totalCompra, ClienteServicio clienteServicio) {
//        this.typeCompra = typeCompra;
//        this.totalCompra = totalCompra;
//        this.clienteServicio = clienteServicio;
//    }

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
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getTotalCompraProducto() {
        return totalCompraProducto;
    }
    public void setTotalCompraProducto(double totalCompraProducto) {
        this.totalCompraProducto = totalCompraProducto;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public ClienteServicio getClienteServicio() {return clienteServicio;}
    public void setClienteServicio(ClienteServicio clienteServicio) {this.clienteServicio = clienteServicio;}

    public ClienteProducto getClienteProducto() {return clienteProducto;}
    public void setClienteProducto(ClienteProducto clienteProducto) {this.clienteProducto = clienteProducto;}

    public Ticket getTicket() {return ticket;}
    public void setTicket(Ticket ticket) {this.ticket = ticket;}

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
