package com.mindhub.newStyle.dtos;

import ch.qos.logback.core.net.server.Client;
import com.mindhub.newStyle.modelos.Cliente;

public class ClienteDTO {

    private String primerNombre;
    private String apellido;
    private String email;
    private String password;
    private String numeroTel = null;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.primerNombre = cliente.getPrimerNombre();
        this.apellido = cliente.getApellido();
        this.email = cliente.getEmail();
        this.password = cliente.getPassword();
        this.numeroTel = cliente.getNumeroTel();
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
}
