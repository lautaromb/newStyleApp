package com.mindhub.newStyle.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    Long id;
    String nombre;
    String email;

    @OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
    private Set<Sucursal> sucursales = new HashSet<>();

    @OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
    private Set<Servicio> servicio = new HashSet<>();

    /*Relacion con Sucursal*/





    public Negocio() {
    }

    public Negocio(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
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

    public Set<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(Set<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public Set<Servicio> getServicio() {
        return servicio;
    }

    public void setServicio(Set<Servicio> servicio) {
        this.servicio = servicio;
    }
}

