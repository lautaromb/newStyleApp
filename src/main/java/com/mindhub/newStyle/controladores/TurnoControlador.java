package com.mindhub.newStyle.controladores;


import com.mindhub.newStyle.dtos.TurnoDTO;
import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.Turno;
import com.mindhub.newStyle.repositorios.RepositorioCliente;
import com.mindhub.newStyle.repositorios.RepositorioServicio;
import com.mindhub.newStyle.repositorios.RepositorioTurno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TurnoControlador {

    @Autowired
    RepositorioCliente repositorioNegocio;

    @Autowired
    RepositorioServicio repositorioServicio;

    @Autowired
    RepositorioTurno repositorioTurno;

    @Autowired
    RepositorioCliente repositorioCliente;

    @PostMapping("/generar/turnos")
    public ResponseEntity<Object> generarTurnos(@RequestBody TurnoDTO turnoDTO, Authentication authentication) {
        Cliente clienteActual = repositorioCliente.findByEmail(authentication.getName());
        Negocio negocio = clienteActual.getNegocio();

        if (turnoDTO == null) {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
        if (turnoDTO.getTipoServicio() == null || turnoDTO.getFechaServicio().isEmpty() || turnoDTO.getHoraServicio() == null) {
            return new ResponseEntity<>("Por favor complete todos los campos", HttpStatus.FORBIDDEN);
        }
        Turno nuevoTurno = new Turno(turnoDTO.getTipoServicio(),turnoDTO.getValor(),turnoDTO.getFechaServicio(),turnoDTO.getHoraServicio(), clienteActual, negocio);

        repositorioTurno.save(nuevoTurno);

        return new ResponseEntity<>("Turno agendado", HttpStatus.CREATED);
    }


}
