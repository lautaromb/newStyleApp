package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private TypeCompra typeCompra;
    private String nombreCliente;
    private String totalCompra;


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
}
