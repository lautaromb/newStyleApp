package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private double valor;
    private String imagenProducto;
    private String imagenCard;
    private int stock;
    private String descripcion;
    private boolean activo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "negocio")
    private Negocio negocio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "compra")
    private Compra compra;



    public Producto() {
    }

    public Producto(String nombre, double valor, String imagenProducto, String imagenCard, int stock, String descripcion, Negocio negocio) {
        this.nombre = nombre;
        this.valor = valor;
        this.imagenProducto = imagenProducto;
        this.imagenCard = imagenCard;
        this.stock = stock;
        this.descripcion = descripcion;
        this.negocio = negocio;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagenProducto() {return imagenProducto;}
    public void setImagenProducto(String imagenProducto) {this.imagenProducto = imagenProducto;}

    public String getImagenCard() {return imagenCard;}
    public void setImagenCard(String imagenCard) {this.imagenCard = imagenCard;}

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Negocio getNegocio() {return negocio;}
    public void setNegocio(Negocio negocio) {this.negocio = negocio;}

    public Compra getCompra() {return compra;}
    public void setCompra(Compra compra) {this.compra = compra;}

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
