//package com.mindhub.newStyle.controladores;
//
//import com.mindhub.newStyle.dtos.SucursalDTO;
//import com.mindhub.newStyle.modelos.Sucursal;
//import com.mindhub.newStyle.repositorios.RepositorioCliente;
//import com.mindhub.newStyle.repositorios.RepositorioProducto;
//import com.mindhub.newStyle.repositorios.RepositorioServicio;
//import com.mindhub.newStyle.servicios.implementaciones.SucursalServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.http.HttpResponse;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class SucursalControlador {
//
//    @Autowired
//    RepositorioCliente repositorioCliente;
//
//    @Autowired
//    SucursalServicio sucursalServicio;
//
//    @Autowired
//    RepositorioServicio repositorioServicio;
//
//    @GetMapping("/sucursales")
//    public List<SucursalDTO> getSucursalesDTO(){
//        return sucursalServicio.getSucursalesDTO();
//    }
//
////    @PostMapping("/sucursales")
////    public ResponseEntity<Object> crearSucursal(@RequestParam String nombre, @RequestParam String email, @RequestParam String direccion,
////                                                @RequestParam String inicioHS, @RequestParam String cierreHS){
////
////
////
////        if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || inicioHS.isEmpty() || cierreHS.isEmpty()){
////            return new ResponseEntity<>("Faltan datos obligatorios", HttpStatus.FORBIDDEN);
////        }
////
////        if(sucursalServicio.findSucursalbyEmail(email) != null){
////            return new ResponseEntity<>("Mail ya registrado", HttpStatus.FORBIDDEN);
////        }
////
////        Sucursal sucursal = new Sucursal(nombre, email, direccion, inicioHS, cierreHS );
////
////    }
//
//}
