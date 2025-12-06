package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.TicketDTO;
import com.mindhub.newStyle.modelos.Ticket;
import com.mindhub.newStyle.repositorios.RepositorioTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DespachoControlador {

    @Autowired
    RepositorioTicket repositorioTicket;

    /**
     * Obtener todas las ventas (tickets) con opción de filtrar
     * @param entregado (opcional) true/false para filtrar, null para ver todas
     */
    @GetMapping("/despacho/ventas")
    public ResponseEntity<Object> obtenerVentas(
            @RequestParam(required = false) Boolean entregado) {

        List<Ticket> tickets;

        // Si se especifica el filtro, filtrar
        if (entregado != null) {
            tickets = repositorioTicket.findAll().stream()
                    .filter(ticket -> ticket.isEntregado() == entregado)
                    .collect(Collectors.toList());
        }
        // Si no, traer todas
        else {
            tickets = repositorioTicket.findAll();
        }

        if (tickets.isEmpty()) {
            return new ResponseEntity<>("No hay ventas registradas", HttpStatus.OK);
        }

        List<TicketDTO> ticketsDTO = tickets.stream()
                .map(TicketDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ticketsDTO, HttpStatus.OK);
    }

    /**
     * Obtener detalle de una venta específica
     */
    @GetMapping("/despacho/ventas/{id}")
    public ResponseEntity<Object> obtenerDetalleVenta(@PathVariable Long id) {

        Ticket ticket = repositorioTicket.findById(id).orElse(null);

        if (ticket == null) {
            return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
        }

        TicketDTO ticketDTO = new TicketDTO(ticket);
        return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
    }

    /**
     * Marcar una venta como entregada
     */
    @PatchMapping("/despacho/ventas/{id}/entregar")
    public ResponseEntity<Object> marcarComoEntregado(@PathVariable Long id) {

        Ticket ticket = repositorioTicket.findById(id).orElse(null);

        if (ticket == null) {
            return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
        }

        if (ticket.isEntregado()) {
            return new ResponseEntity<>("Esta venta ya fue entregada", HttpStatus.FORBIDDEN);
        }

        ticket.setEntregado(true);
        repositorioTicket.save(ticket);

        return new ResponseEntity<>("Venta marcada como entregada", HttpStatus.OK);
    }

    /**
     * Desmarcar una venta como entregada (por si se marcó por error)
     */
    @PatchMapping("/despacho/ventas/{id}/no-entregar")
    public ResponseEntity<Object> desmarcarEntregado(@PathVariable Long id) {

        Ticket ticket = repositorioTicket.findById(id).orElse(null);

        if (ticket == null) {
            return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
        }

        if (!ticket.isEntregado()) {
            return new ResponseEntity<>("Esta venta no está marcada como entregada", HttpStatus.FORBIDDEN);
        }

        ticket.setEntregado(false);
        repositorioTicket.save(ticket);

        return new ResponseEntity<>("Venta desmarcada como entregada", HttpStatus.OK);
    }
}