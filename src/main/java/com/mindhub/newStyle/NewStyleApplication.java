package com.mindhub.newStyle;

import com.mindhub.newStyle.modelos.*;
import com.mindhub.newStyle.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class NewStyleApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(NewStyleApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(RepositorioNegocio repositorioNegocio,
									  RepositorioCliente repositorioCliente,
									  RepositorioProducto repositorioProducto,
									  RepositorioServicio repositorioServicio,
									  RepositorioClienteProducto repositorioClienteProducto,
									  RepositorioClienteServicio repositorioClienteServicio,
									  RepositorioCompra repositorioCompra,
									  RepositorioTicket repositorioTicket
									 ){
		return (args) ->{

			Negocio newStyle = new Negocio("New Style", "newStyle@gmail.com", "Av. de Mayo 2859");


			Servicio servicioCorteDePelo = new Servicio(TipoServicio.CORTE, 400.0, "https://i.imgur.com/gSPyRXh.jpg", "url aqui","Corte de cabello unisex a elección del cliente", newStyle);
			Servicio servicioTintura = new Servicio(TipoServicio.TINTURA, 1500.0, "https://i.imgur.com/b41Oddh.jpg", "url", "Ballayage, colores fantasia, cubrimiento de canas", newStyle);
			Servicio servicioAlisado = new Servicio(TipoServicio.ALISADO, 1100.0, "https://i.imgur.com/FhdJ2mJ.jpg", "url aqui","Tratamiento capilares,alisado chocolate y alisado forte", newStyle);
			Servicio servicioBarberia = new Servicio(TipoServicio.BARBA, 200.0,  "https://i.imgur.com/aW2KwyG.jpg", "url aqui","Emparejamiento, perfilamiento a eleccion del cliente", newStyle);
			Servicio servicioManicura = new Servicio(TipoServicio.MANICURA, 500.0,  "https://i.imgur.com/FXvwcNQ.jpg", "url aqui","Uñas acrilicas, uñas de gel, esmaltado semipermanente", newStyle);
			Servicio servicioPedicura = new Servicio(TipoServicio.PEDICURA, 500.0,  "https://i.imgur.com/0b39TL5.jpg", "url aqui","Belleza de pies, esmaltado semipermanente,capping gel", newStyle);
			Servicio servicioPestaña = new Servicio(TipoServicio.PESTAÑAS, 800.0,  "https://i.imgur.com/yXrm71e.jpg", "url aqui","Extenciones pelo a pelo sobre la pestaña natural", newStyle);
			Servicio servicioPerfiladoCejas = new Servicio(TipoServicio.CEJAS, 300.0,  "https://i.imgur.com/RYY9j0R.jpg", "url aqui","Modelado de cejas de acuerdo con tu rosto, ojos y nariz", newStyle);

			Producto productoEnjuague = new Producto("Shampoo de coco ", 650.0, "https://i.imgur.com/JgSoduV.jpg", "url aqui", 8, "Lavado a profundidad con un aroma particular a coco", newStyle);
			Producto productoKitMaquillajeFacial = new Producto("Kit maquillaje ojos y cejas ", 350.0, "https://i.imgur.com/w8ZANSo.jpg ", "url aqui", 15, "Ideal para rostros sensibles, pinceles de calidad", newStyle);
			Producto productoEsmalte = new Producto("Esmalte elite varios colores ", 350.0, "https://i.imgur.com/YQO9z2A.jpg", "url aqui", 20, "Diversos esmalte de varios colores para elegir a gusto", newStyle);
			Producto productoExfoliante= new Producto("Filorga exfoliante", 900.0, "https://i.imgur.com/0JEtpx7.jpg", "url aqui", 5, "Exfoliante facial para quitar células muertas, prevee acné", newStyle);
			Producto productoKitMaquillaje = new Producto("Kit maquillaje facial", 5000.0, "https://i.imgur.com/paxQA4x.jpg", "url aqui", 2, "Combo maquillaje de 32 piezas con pinceles gruesos", newStyle);
			Producto productoKitBarberia = new Producto("Kit barberia principiante", 3800.0, "https://i.imgur.com/qVheV7y.jpg", "url aqui",4, "Kit esencial para dar tus primeros pasos como barbero", newStyle);
			Producto productoNavaja = new Producto("Navaja dorada", 1000.0, "https://i.imgur.com/8WDeUnX.jpg", "url aqui",4, "Navaja para realizar degrade, como para afeitar al raz", newStyle);
			Producto productoMaquinaPelo = new Producto("Cortadora de pelo azul", 5800.0, "https://i.imgur.com/mKfPv7w.jpg", "url aqui",4, "Maquina para cortar pelo babyliss 3000 rpm", newStyle);


			Cliente admin = new Cliente("Admin", "New Style", "admin@admin.com", passwordEncoder.encode("admin"),"0000");
			Cliente cliente1 = new Cliente("User", "Resu", "user@gmail.com", passwordEncoder.encode("user123"),"0000");
			Cliente cliente2 = new Cliente("Martha", "Stuart Little", "msl@gmail.com", passwordEncoder.encode("user123"),"0000");
			Cliente cliente3 = new Cliente("Pedro", "Gomez", "pedro@gmail.com", passwordEncoder.encode("user123"),"0000");
			Cliente cliente4 = new Cliente("Mauro", "Perez", "mauro@gmail.com", passwordEncoder.encode("user123"),"0000");

//casd





			repositorioNegocio.save(newStyle);

			repositorioCliente.save(cliente1);
			repositorioCliente.save(admin);

			repositorioServicio.save(servicioCorteDePelo);
			repositorioServicio.save(servicioBarberia);
			repositorioServicio.save(servicioAlisado);
			repositorioServicio.save(servicioTintura);
			repositorioServicio.save(servicioManicura);
			repositorioServicio.save(servicioPedicura);
			repositorioServicio.save(servicioPestaña);
			repositorioServicio.save(servicioPerfiladoCejas);

			repositorioProducto.save(productoEnjuague);
			repositorioProducto.save(productoKitMaquillajeFacial);
			repositorioProducto.save(productoEsmalte);
			repositorioProducto.save(productoExfoliante);
			repositorioProducto.save(productoKitMaquillaje);
			repositorioProducto.save(productoKitBarberia);
			repositorioProducto.save(productoNavaja);
			repositorioProducto.save(productoMaquinaPelo);

//
//			repositorioTicket.save(ticket);
//			repositorioCompra.save(compra);
//			repositorioClienteProducto.save(clienteProducto);


			//repositorioClienteProducto.save(clienteProducto);
			//repositorioClienteServicio.save(clienteServicio1);


//			repositorioCompra.save(compra);
//			repositorioCompra.save(compra2);
//			repositorioCompra.save(compra3);
//
//			repositorioTicket.save(ticket);



		};
	}


}


