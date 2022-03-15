package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private TypeCompra typeCompra;
    private String nombreCliente;
    private String totalCompra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_producto")
    private ClienteProducto clienteProducto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_servicio")
    private ClienteServicio clienteServicio;


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "producto_id")
//    private Producto producto;

    public Compra() {
    }

    public Compra(TypeCompra typeCompra, String nombreCliente, String totalCompra) {
        this.typeCompra = typeCompra;
        this.nombreCliente = nombreCliente;
        this.totalCompra = totalCompra;
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
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTotalCompra() {
        return totalCompra;
    }
    public void setTotalCompra(String totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public ClienteServicio getClienteServicio() {return clienteServicio;}
    public void setClienteServicio(ClienteServicio clienteServicio) {this.clienteServicio = clienteServicio;}
}
