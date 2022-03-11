package com.mindhub.newStyle.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class SucursalServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "servicio_id")
    private Servicio servicio;

    public SucursalServicio() {
    }

    public SucursalServicio(Sucursal sucursal, Servicio servicio) {
        this.sucursal = sucursal;
        this.servicio = servicio;
    }

    public long getId() {
        return id;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}
