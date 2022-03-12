package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private double valor;
    private String imagenPrincipal;
    private String imagenDesc1;
    private String imagenDesc2;
    private String descripcion;
    private String horario;
    private String atencion;


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
        this.valor = precio;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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
