package com.mindhub.newStyle.modelos;

import com.mindhub.newStyle.repositorios.RepositorioNegocio;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private double valor;
    private String imagenServicio;
    private String imagenCard;
    private String descripcion;
    private boolean activo;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="negocio")
    private Negocio negocio;

    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
    private Set <ClienteServicio> clienteServicio = new HashSet<>();



//    @Autowired
//    RepositorioNegocio repositorioNegocio;

    //Negocio newStyle = repositorioNegocio.findByEmail("newStyle@gmail.com");

//    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
//    private Set<SucursalServicio> sucursalServicios = new HashSet<>();

    //relacion n a n con Sucursal


    public Servicio() {
    }


    public Servicio(String nombre, double valor, String imagenServicio, String imagenCard, String descripcion, Negocio negocio) {
        this.nombre = nombre;
        this.valor = valor;
        this.imagenServicio = imagenServicio;
        this.imagenCard = imagenCard;
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

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }


    public String getImagenServicio() {return imagenServicio;}

    public void setImagenServicio(String imagenServicio) {this.imagenServicio = imagenServicio;}

    public String getImagenCard() {return imagenCard;}

    public void setImagenCard(String imagenCard) {
        this.imagenCard = imagenCard;
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

    public Set<ClienteServicio> getClienteServicio() {
        return clienteServicio;
    }
    public void setClienteServicio(Set<ClienteServicio> clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
