package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Producto;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private double precio;
    private int stock;
    private String imagenProducto;
    private String imagenCard;
    private String descripcion;
    private boolean activo;

    public ProductoDTO() {
    }

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.precio = producto.getValor();
        this.imagenProducto = producto.getImagenProducto();
        this.imagenCard = producto.getImagenCard();
        this.stock = producto.getStock();
        this.descripcion = producto.getDescripcion();
        this.activo = producto.isActivo();
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

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String getImagenCard() {
        return imagenCard;
    }

    public void setImagenCard(String imagenCard) {
        this.imagenCard = imagenCard;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
