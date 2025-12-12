package com.mindhub.newStyle.controladores;

import com.mindhub.newStyle.dtos.ProductoDTO;
import com.mindhub.newStyle.modelos.Negocio;
import com.mindhub.newStyle.modelos.Producto;
import com.mindhub.newStyle.modelos.Servicio;
import com.mindhub.newStyle.repositorios.RepositorioNegocio;
import com.mindhub.newStyle.repositorios.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductoControlador {

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioNegocio repositorioNegocio;

    @GetMapping("/producto")
    public Set<ProductoDTO> getProductoDTOS(){
    Set<ProductoDTO> productoDTOS = repositorioProducto.findAll().stream().map(ProductoDTO::new).collect(Collectors.toSet());
    return productoDTOS;
    }

    @PostMapping("/producto")
    public ResponseEntity<Object> createProducto(@RequestBody ProductoDTO productoDTO){

        Negocio negocio = repositorioNegocio.findByEmail("newStyle@gmail.com");

        if (productoDTO.getNombre().isEmpty() || productoDTO.getPrecio() <= 0 ||
                productoDTO.getImagenProducto().isEmpty() || productoDTO.getImagenCard().isEmpty() ||
                productoDTO.getDescripcion().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Producto producto = new Producto(productoDTO.getNombre(), productoDTO.getPrecio(),
                productoDTO.getImagenProducto(), productoDTO.getImagenCard(),
                productoDTO.getStock(), productoDTO.getDescripcion(), negocio);

        repositorioProducto.save(producto);

        return new ResponseEntity<>("Producto creado", HttpStatus.CREATED);
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<Object> editarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){

        Producto producto = repositorioProducto.findById(id).orElse(null);

        if(producto == null){
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        producto.setNombre(productoDTO.getNombre());
        producto.setValor(productoDTO.getPrecio());
        producto.setImagenProducto(productoDTO.getImagenProducto());
        producto.setImagenCard(productoDTO.getImagenCard());
        producto.setStock(productoDTO.getStock());
        producto.setDescripcion(productoDTO.getDescripcion());

        repositorioProducto.save(producto);

        return new ResponseEntity<>("Producto actualizado", HttpStatus.OK);
    }

    // Eliminar producto
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Object> eliminarProducto(@PathVariable Long id){

        Producto producto = repositorioProducto.findById(id).orElse(null);

        if(producto == null){
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        producto.setActivo(false);
        repositorioProducto.save(producto);

        return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
    }

}
