package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.ClienteProducto;
import com.mindhub.newStyle.modelos.Compra;
import com.mindhub.newStyle.modelos.Producto;
import net.minidev.json.annotate.JsonIgnore;


public class ClienteProductoDTO {

    @JsonIgnore
    private Cliente cliente;
    @JsonIgnore
    private Producto producto;
    @JsonIgnore
    private Compra compra;

    public ClienteProductoDTO() {
    }

    public ClienteProductoDTO(ClienteProducto clienteProducto) {
        this.cliente = clienteProducto.getCliente();
        this.producto = clienteProducto.getProducto();
        this.compra = clienteProducto.getCompra();
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
