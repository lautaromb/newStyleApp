package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String primerNombre;
    private String apellido;
    private String email;
    private String password;
    private String numeroTel = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "negocio")
    private Negocio negocio;


    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    private Set<ClienteServicio> clienteServicio = new HashSet<>();

    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    private Set<ClienteProducto> clienteProducto = new HashSet<>();

    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    private Set<Turno> turnos = new HashSet<>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private Set<Ticket> tickets = new HashSet<>();

    public Cliente() {
    }

    public Cliente(String primerNombre, String apellido, String email, String password, String numeroTel) {
        this.primerNombre = primerNombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.numeroTel = numeroTel;
    }

    public Long getId() {
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

    public Set<ClienteServicio> getClienteServicio() {return clienteServicio;}
    public void setClienteServicio(Set<ClienteServicio> clienteServicio) {this.clienteServicio = clienteServicio;}

    public Set<ClienteProducto> getClienteProducto() {return clienteProducto;}
    public void setClienteProducto(Set<ClienteProducto> clienteProducto) {this.clienteProducto = clienteProducto;}

    public Set<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
