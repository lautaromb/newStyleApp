package com.mindhub.newStyle.modelos;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private String email;
    private String direccion;

    @OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
    private Set<Cliente> clientes = new HashSet<>();

    @OneToMany(mappedBy = "negocio", fetch = FetchType.EAGER)
    private Set<Servicio> servicio = new HashSet<>();

    @OneToMany(mappedBy = "negocio",fetch = FetchType.EAGER)
    private Set<Turno> turnos = new HashSet<>();

    /*Relacion con Sucursal*/





    public Negocio() {
    }

    public Negocio(String nombre, String email, String direccion) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
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

    public Set<Cliente> getClientes() {
        return clientes;
    }
    public void setClientess(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Set<Servicio> getServicio() {
        return servicio;
    }
    public void setServicio(Set<Servicio> servicio) {
        this.servicio = servicio;
    }
}

