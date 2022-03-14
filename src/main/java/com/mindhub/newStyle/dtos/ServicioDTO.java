package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.Servicio;

public class ServicioDTO {
    private Long id;
    private String nombre;
    private double valor;
    private String imagenPrincipal;
    private String imagenDesc1;
    private String imagenDesc2;
    private String descripcion;
    private Negocio negocio;

    public ServicioDTO() {
    }

    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.nombre = servicio.getNombre();
        this.valor = servicio.getValor();
        this.imagenPrincipal = servicio.getImagenPrincipal();
        this.imagenDesc1 = servicio.getImagenDesc1();
        this.imagenDesc2 = servicio.getImagenDesc2();
        this.descripcion = servicio.getDescripcion();
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

    public String getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(String imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public String getImagenDesc1() {
        return imagenDesc1;
    }

    public void setImagenDesc1(String imagenDesc1) {
        this.imagenDesc1 = imagenDesc1;
    }

    public String getImagenDesc2() {
        return imagenDesc2;
    }

    public void setImagenDesc2(String imagenDesc2) {
        this.imagenDesc2 = imagenDesc2;
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
}
