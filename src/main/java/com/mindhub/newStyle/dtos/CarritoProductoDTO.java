package com.mindhub.newStyle.dtos;

public class CarritoProductoDTO {

    private boolean activo;
    private int cantidad;
    private String descripcion;
    private long id;
    private String imagenCard;
    private String imagenProducto;
    private String nombre;
    private double precio;
    private int stock;


    public CarritoProductoDTO() {
    }

    public CarritoProductoDTO(boolean activo, int cantidad, String descripcion, long id, String imagenCard, String imagenProducto, String nombre, double precio, int stock) {
        this.activo = activo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.id = id;
        this.imagenCard = imagenCard;
        this.imagenProducto = imagenProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public long getId() {
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

    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getImagenCard() {
        return imagenCard;
    }
    public void setImagenCard(String imagenCard) {
        this.imagenCard = imagenCard;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }
    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }
}
