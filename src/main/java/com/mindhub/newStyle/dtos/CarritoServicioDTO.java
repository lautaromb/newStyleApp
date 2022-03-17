package com.mindhub.newStyle.dtos;

public class CarritoServicioDTO {

    private long idProducto;
    private String nombre;
    private double valor;
    private int cantidad;


    public CarritoServicioDTO() {
    }

    public CarritoServicioDTO(long idProducto, String nombre, double valor, int cantidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
        this.cantidad = cantidad;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
