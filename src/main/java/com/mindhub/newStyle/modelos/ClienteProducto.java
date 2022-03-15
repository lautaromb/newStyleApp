package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClienteProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Producto_id")
    private Producto  producto;


    public ClienteProducto() {
    }

    public ClienteProducto(Cliente cliente, Producto producto) {
        this.cliente = cliente;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }


    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {this.producto = producto;}
}
