package com.mindhub.newStyle.dtos;

import ch.qos.logback.core.net.server.Client;
import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.Negocio;

public class ClienteDTO {
    private long id;
    private String primerNombre;
    private String apellido;
    private String email;
    private String password;
    private String numeroTel = null;
    private Negocio negocio;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.primerNombre = cliente.getPrimerNombre();
        this.apellido = cliente.getApellido();
        this.email = cliente.getEmail();
        this.password = cliente.getPassword();
        this.numeroTel = cliente.getNumeroTel();
        this.negocio = cliente.getNegocio();
    }

    /*Este es un comentario 2*/

    public long getId() {
        return id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumeroTel() {
        return numeroTel;
    }
    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public Negocio getNegocio() {
        return negocio;
    }
    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }
}
