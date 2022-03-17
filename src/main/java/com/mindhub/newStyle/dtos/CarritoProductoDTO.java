package com.mindhub.newStyle.dtos;

public class CarritoProductoDTO {

    private long idProducto;
    private String nombre;
    private double valor;
    private int cantidad;


    public CarritoProductoDTO() {
    }

    public CarritoProductoDTO(long idProducto, String nombre, double valor, int cantidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
        this.cantidad = cantidad;
    }

    public long getIdProducto() {
        return idProducto;
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
