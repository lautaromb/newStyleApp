package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.Servicio;

import java.util.HashSet;
import java.util.Set;

public class NegocioDTO {
    private Long id;
    private String nombre;
    private String email;
    private Set<Cliente> clientes = new HashSet<>();
    private Set<Servicio> servicio = new HashSet<>();

    public NegocioDTO() {
    }

    public NegocioDTO(Negocio negocio) {
        this.id = negocio.getId();
        this.nombre = negocio.getNombre();
        this.email = negocio.getEmail();
        this.clientes = negocio.getClientes();
        this.servicio = negocio.getServicio();
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

    public Set<Cliente> getClientes() {
        return clientes;
    }
    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Set<Servicio> getServicio() {
        return servicio;
    }
    public void setServicio(Set<Servicio> servicio) {
        this.servicio = servicio;
    }

}
