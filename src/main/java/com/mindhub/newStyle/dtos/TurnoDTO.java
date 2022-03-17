package com.mindhub.newStyle.dtos;

import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.TipoServicio;
import com.mindhub.newStyle.modelos.Turno;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class TurnoDTO {

    private Long id;
    private TipoServicio tipoServicio;
    private double valor;
    private String fechaServicio;
    private String horaServicio;
    private Cliente cliente;
    private Negocio negocio;

    public TurnoDTO() {
    }


    public TurnoDTO(Turno turno) {
        this.tipoServicio = turno.getTipoServicio();
        this.valor = turno.getValor();
        this.fechaServicio = turno.getFechaServicio();
        this.horaServicio = turno.getHoraServicio();
        this.cliente = turno.getCliente();
        this.negocio = turno.getNegocio();
    }


    public Long getId() {
        return id;
    }


    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(String fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(String horaServicio) {
        this.horaServicio = horaServicio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }
}
