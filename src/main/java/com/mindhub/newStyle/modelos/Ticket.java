package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private double totalCompraValor;
    private boolean entregado = false;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private LocalDateTime fecha;


    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
     Set<Compra> compras = new HashSet<>();

    public Ticket() {
    }

    public Ticket( double totalCompraValor) {
        this.totalCompraValor = totalCompraValor;
    }

    public Ticket(Cliente cliente) {
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {return id;}

    public Set<Compra> getCompras() {return compras;}
    public void setCompras(Set<Compra> compras) {this.compras = compras;}


    public double getTotalCompraValor() {return totalCompraValor;}
    public void setTotalCompraValor(double totalCompraValor) {this.totalCompraValor = totalCompraValor;}


}
