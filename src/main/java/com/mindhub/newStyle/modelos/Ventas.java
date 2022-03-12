//package com.mindhub.newStyle.models;
//
//
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
//@Entity
//public class Ventas {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
//    private Long id;
//
//        @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name= "sucursal_id")
//    private Sucursal sucursal;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name= "servicio_id")
//    private Servicio servicio;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name= "servicio_id")
//    private Servicio servicio;
//
//
//
//
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="negocio")
//    private Negocio negocio;
//
//    public Ventas() {
//    }
//
//
//
//}
