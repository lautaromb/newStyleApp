//package com.mindhub.newStyle.models;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//public class SucursalServicio {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
//    private long id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name= "sucursal_id")
//    private Sucursal sucursal;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name= "servicio_id")
//    private Servicio servicio;
//
////    private ArrayList<Object> servicios = new ArrayList<>();
//
//    public SucursalServicio() {
//    }
//
//    public SucursalServicio(Sucursal sucursal /*ArrayList servicios*/ ,Servicio servicio) {
//        this.sucursal = sucursal;
//        this.servicio = servicio;
////        this.servicios = servicios;
////        servicios.add(this);
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public Sucursal getSucursal() {
//        return sucursal;
//    }
//
//    public void setSucursal(Sucursal sucursal) {
//        this.sucursal = sucursal;
//    }
//
//    public Servicio getServicio() {
//        return servicio;
//    }
//
//    public void setServicio(Servicio servicio) {
//        this.servicio = servicio;
//    }
//
////    public ArrayList<Object> getServicios() {
////        return servicios;
////    }
////
////    public void setServicios(ArrayList<Object> servicios) {
////        this.servicios = servicios;
////    }
//}
