package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.InformeProductoDTO;
import com.mindhub.newStyle.modelos.ClienteProducto;
import com.mindhub.newStyle.modelos.Compra;
import com.mindhub.newStyle.repositorios.RepositorioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class InformeControlador {

    @Autowired
    RepositorioCompra repositorioCompra;

    @GetMapping("/informes/productos")
    public ResponseEntity<Object> obtenerInformeProductos() {

// 1. Obtener todas las compras
        List<Compra> todasLasCompras = repositorioCompra.findAll();

        if (todasLasCompras.isEmpty()) {
            return new ResponseEntity<>("No hay compras registradas", HttpStatus.OK);
        }

// 2. Mapa para acumular datos
        Map<String, InformeProductoDTO> informeMap = new HashMap<>();

// 3. Recorrer todas las compras y luego todos sus productos
        for (Compra compra : todasLasCompras) {

            for (ClienteProducto cp : compra.getClienteProductos()) {

                String nombreProducto = cp.getProducto().getNombre();

                int cantidad = cp.getCantidad();
                double subtotal = cp.getSubtotal();

                if (informeMap.containsKey(nombreProducto)) {

                    InformeProductoDTO informe = informeMap.get(nombreProducto);

                    // Sumar cantidades
                    informe.setCantidadVendida(
                            informe.getCantidadVendida() + cantidad
                    );

                    // Sumar totales
                    informe.setTotalRecaudado(
                            informe.getTotalRecaudado() + subtotal
                    );

                } else {

                    InformeProductoDTO nuevoInforme = new InformeProductoDTO(
                            nombreProducto,
                            cantidad,
                            subtotal
                    );

                    informeMap.put(nombreProducto, nuevoInforme);
                }
            }
        }

// Convertir a lista y ordenar
        List<InformeProductoDTO> informes = new ArrayList<>(informeMap.values());
        informes.sort((a, b) -> Double.compare(b.getTotalRecaudado(), a.getTotalRecaudado()));

        return new ResponseEntity<>(informes, HttpStatus.OK);
    }
}