//package com.mindhub.newStyle.servicios;
//
//import com.mindhub.newStyle.dtos.SucursalDTO;
//import com.mindhub.newStyle.modelos.Sucursal;
//import com.mindhub.newStyle.repositorios.RepositorioSucursal;
//import com.mindhub.newStyle.servicios.implementaciones.SucursalServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class SucursalImplementacion implements SucursalServicio {
//
//    @Autowired
//    RepositorioSucursal repositorioSucursal;
//
//    @Override
//    public Sucursal saveSucursal(Sucursal sucursal) {
//        return repositorioSucursal.save(sucursal);
//    }
//
//    @Override
//    public List<SucursalDTO> getSucursalesDTO() {
//        return repositorioSucursal.findAll().stream().map(SucursalDTO::new).collect(Collectors.toList());
//    }
//
//    @Override
//    public Sucursal findSucursalbyEmail(String email) {
//        return repositorioSucursal.findByEmail(email);
//    }
//}
