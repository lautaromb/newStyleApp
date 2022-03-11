package com.mindhub.newStyle.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    Long id;
    String nombre;
    String email;
    String direccion;
    String inicioHS; //ejemplo "8:00"
    String cierreHS; //ejemplo "21:00"

    ZoneId zona = ZoneId.systemDefault();
    LocalDate hoy = LocalDate.now();

    //Datos de horarios
//    LocalTime inicioHS = LocalTime.of(8, 0); //Sucursal abre a las 08:00
//    LocalTime cierreHS = LocalTime.of(21, 0); // Sucursal cierra a las 21:00

    //Horarios de atencion al publico

//    ZonedDateTime sucursalAbre = hoy.atTime(LocalTime.parse(inicioHS)).atZone(zona);
//    ZonedDateTime sucursalCierra = hoy.atTime(LocalTime.parse(cierreHS)).atZone(zona);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="negocio")
    private Negocio negocio;

//    @OneToMany(mappedBy = "sucursal", fetch = FetchType.EAGER)
//    private Set<SucursalServicio> sucursalServicios = new HashSet<>();



    public Sucursal() {
    }


    public Sucursal(String nombre, String email, String direccion, String inicioHS, String cierreHS, Negocio negocio) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.inicioHS = inicioHS;
        this.cierreHS = cierreHS;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public ZoneId getZona() {
        return zona;
    }

    public void setZona(ZoneId zona) {
        this.zona = zona;
    }

    public LocalDate getHoy() {
        return hoy;
    }

    public void setHoy(LocalDate hoy) {
        this.hoy = hoy;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
 //   }

    //    public ZonedDateTime getSucursalAbre() {
//        return sucursalAbre;
//    }
//
//    public void setSucursalAbre(ZonedDateTime sucursalAbre) {
//        this.sucursalAbre = sucursalAbre;
//    }
//
//    public ZonedDateTime getSucursalCierra() {
//        return sucursalCierra;
//    }
//
//    public void setSucursalCierra(ZonedDateTime sucursalCierra) {
//        this.sucursalCierra = sucursalCierra;
//    }

}
