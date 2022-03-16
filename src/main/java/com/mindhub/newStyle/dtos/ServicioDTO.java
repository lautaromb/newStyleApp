package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.Servicio;

public class ServicioDTO {
    private Long id;
    private String nombre;
    private double valor;
    private String imagenServicio;
    private String imagenCard;
    private String descripcion;
    private Negocio negocio;
    private boolean activo;

    public ServicioDTO() {
    }

    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.nombre = servicio.getNombre();
        this.valor = servicio.getValor();
        this.imagenServicio = servicio.getImagenServicio();
        this.imagenCard = servicio.getImagenCard();
        this.descripcion = servicio.getDescripcion();
        this.activo = servicio.isActivo();
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

    public String getImagenServicio() {
        return imagenServicio;
    }
    public void setImagenServicio(String imagenServicio) {
        this.imagenServicio = imagenServicio;
    }

    public String getImagenCard() {
        return imagenCard;
    }
    public void setImagenCard(String imagenCard) {
        this.imagenCard = imagenCard;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
