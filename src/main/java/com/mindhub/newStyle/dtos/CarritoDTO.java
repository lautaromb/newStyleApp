package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.TypeCompra;

public class CarritoDTO {

    private TypeCompra typeCompra;
    private long productoID;
    private int stock;


    public CarritoDTO() {
    }

    public CarritoDTO(TypeCompra typeCompra, long productoID, int stock) {
        this.typeCompra = typeCompra;
        this.productoID = productoID;
        this.stock = stock;
    }

    public TypeCompra getTypeCompra() {
        return typeCompra;
    }

    public void setTypeCompra(TypeCompra typeCompra) {
        this.typeCompra = typeCompra;
    }

    public long getProductoID() {
        return productoID;
    }

    public void setProductoID(long productoID) {
        this.productoID = productoID;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
