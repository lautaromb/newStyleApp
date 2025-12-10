package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.ClienteProducto;
import com.mindhub.newStyle.modelos.Compra;
import com.mindhub.newStyle.modelos.Producto;
import net.minidev.json.annotate.JsonIgnore;


public class ClienteProductoDTO {

    private Long id;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public ClienteProductoDTO(ClienteProducto cp) {
        this.id = cp.getId();
        this.nombreProducto = cp.getProducto().getNombre();
        this.cantidad = cp.getCantidad();
        this.precioUnitario = cp.getPrecioUnitario();
        this.subtotal = cp.getSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
