package com.mindhub.newStyle.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
     Set<Compra> compras = new HashSet<>();

    public Ticket() {
    }

    public Ticket(Set<Compra> compras) {
        this.compras = compras;
    }

    
    public Long getId() {return id;}

    public Set<Compra> getCompras() {return compras;}

    public void setCompras(Set<Compra> compras) {this.compras = compras;}

}