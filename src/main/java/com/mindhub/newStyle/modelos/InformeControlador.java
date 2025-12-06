package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.InformeProductoDTO;
import com.mindhub.newStyle.modelos.Compra;
import com.mindhub.newStyle.repositorios.RepositorioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

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

        // 2. Agrupar por nombre de producto y sumar cantidades/totales
        Map<String, InformeProductoDTO> informeMap = new HashMap<>();

        for (Compra compra : todasLasCompras) {
            String nombreProducto = compra.getNombreProducto();

            // Si el producto ya está en el mapa, sumamos
            if (informeMap.containsKey(nombreProducto)) {
                InformeProductoDTO informe = informeMap.get(nombreProducto);
                informe.setCantidadVendida(informe.getCantidadVendida() + compra.getStock());
                informe.setTotalRecaudado(informe.getTotalRecaudado() + compra.getTotalCompraProducto());
            }
            // Si no está, lo creamos
            else {
                InformeProductoDTO nuevoInforme = new InformeProductoDTO(
                        nombreProducto,
                        compra.getStock(),
                        compra.getTotalCompraProducto()
                );
                informeMap.put(nombreProducto, nuevoInforme);
            }
        }

        // 3. Convertir el mapa a lista
        List<InformeProductoDTO> informes = new ArrayList<>(informeMap.values());

        // 4. Ordenar por total recaudado (de mayor a menor)
        informes.sort((a, b) -> Double.compare(b.getTotalRecaudado(), a.getTotalRecaudado()));

        return new ResponseEntity<>(informes, HttpStatus.OK);
    }
}