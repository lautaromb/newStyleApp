package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Sucursal;

public class SucursalDTO {
    private Long id;
    private String nombre;
    private String email;
    private String direccion;
    private String inicioHS;
    private String cierreHS;

    public SucursalDTO() {
    }

    public SucursalDTO(Sucursal sucursal) {
        this.id = sucursal.getId();
        this.nombre = sucursal.getNombre();
        this.email = sucursal.getEmail();
        this.direccion = sucursal.getDireccion();
        this.inicioHS = sucursal.getInicioHS();
        this.cierreHS = sucursal.getCierreHS();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getInicioHS() {
        return inicioHS;
    }

    public void setInicioHS(String inicioHS) {
        this.inicioHS = inicioHS;
    }

    public String getCierreHS() {
        return cierreHS;
    }

    public void setCierreHS(String cierreHS) {
        this.cierreHS = cierreHS;
    }
}
