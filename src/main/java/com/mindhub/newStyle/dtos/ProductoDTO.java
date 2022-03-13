package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Producto;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;

    public ProductoDTO() {
    }

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.precio = producto.getValor();
        this.stock = producto.getStock();
        this.descripcion = producto.getDescripcion();
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
