package com.mindhub.newStyle.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    Long id;
    String nombre;
    double precio;
    String imagenPrincipal;
    String imagenDesc1;
    String imagenDesc2;
    String descripcion;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="negocio")
    private Negocio negocio;

//    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
//    private Set<SucursalServicio> sucursalServicios = new HashSet<>();

    //relacion n a n con Sucursal


    public Servicio() {
    }

    public Servicio( String nombre, double precio, String imagen,String imagenDesc1,String imagenDesc2, String descripcion, Negocio negocio) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenPrincipal = imagen;
        this.imagenDesc1 = imagenDesc1;
        this.imagenDesc2 = imagenDesc2;
        this.descripcion = descripcion;
        this.negocio = negocio;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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

    //    public Set<SucursalServicio> getSucursalServicios() {
//        return sucursalServicios;
//    }
//
//    public void setSucursalServicios(Set<SucursalServicio> sucursalServicios) {
//        this.sucursalServicios = sucursalServicios;
//    }
}
