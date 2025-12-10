package com.mindhub.newStyle.dtos;

public class InformeProductoDTO {

    private String nombreProducto;
    private int cantidadVendida;
    private double totalRecaudado;

    public InformeProductoDTO(String nombreProducto, int cantidadVendida, double totalRecaudado) {
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.totalRecaudado = totalRecaudado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(double totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }
}
