package com.mindhub.newStyle;

import com.mindhub.newStyle.modelos.*;
import com.mindhub.newStyle.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

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
									  RepositorioServicio repositorioServicio
									 ){
		return (args) ->{

			Negocio newStyle = new Negocio("New Style", "newStyle@gmail.com", "Av. de Mayo 2859");

//			Sucursal sucursalCaba = new Sucursal("New Style Caballito", "newStyleCaballito@gmail.com", "Av. Cordoba 2350", "8:00", "21:00", newStyle);
//			Sucursal sucursalZO = new Sucursal("New Style Ramos Mejía", "newStyleRamosMejia@gmail.com", "Av. de Mayo 2042", "8:00", "21:00", newStyle);
//			Sucursal sucursalZS = new Sucursal("New Style Adrogue", "newStyleAdrogue@gmail.com", "Av. Espora 2042", "8:00", "21:00", newStyle);

			Servicio servicioCorteDePelo = new Servicio("Peluquería", 400.0, "https://i.imgur.com/P3HUPFc.jpeg", "url aqui", "url aqui","Corte Lavado Enguaje");
			Servicio servicioTintura = new Servicio("Tintura", 700.0, "url aqui", "url aqui", "url aqui","Lavado, tintura, enjueague");
			Servicio servicioAlisado = new Servicio("Alisado", 1100.0, "url aqui", "url aqui", "url aqui","Tratamiento de alisado");
			Servicio servicioBarberia = new Servicio("Barbería", 200.0, "url aqui", "url aqui", "url aqui","Corte Aceitado");
			Servicio servicioManicura = new Servicio("Manicura", 500.0, "url aqui", "url aqui", "url aqui","Limpieza, esmaltado");
			Servicio servicioPedicura = new Servicio("Pedicura", 500.0, "url aqui", "url aqui", "url aqui","Limpieza y esmaltado");

			Cliente admin = new Cliente("Admin", "New Style", "admind@admin.com", passwordEncoder.encode("admin789"),"0000");
			Cliente cliente1 = new Cliente("User", "Resu", "user@gmail.com", passwordEncoder.encode("user123"),"0000");

//
//			ArrayList<Object> servicios = new ArrayList<>();
//			servicios.add(servicioBarberia);
//			servicios.add(servicioCorteDePelo);


//			SucursalServicio sucursalServicioCaba1 = new SucursalServicio(sucursalCaba, servicioBarberia);
//			SucursalServicio sucursalServicioCaba2 = new SucursalServicio(sucursalCaba, servicioCorteDePelo);


			//SucursalServicio sucursalServicioCaba = new SucursalServicio(sucursalCaba, servicios);
			//SucursalServicio sucursalServicioCaba2 = new SucursalServicio(sucursalCaba, servicioBarberia);


//			System.out.println(servicios);

			repositorioNegocio.save(newStyle);
//			repositorioSucursal.save(sucursalCaba);
//			repositorioSucursal.save(sucursalZO);
//			repositorioSucursal.save(sucursalZS);



			repositorioServicio.save(servicioCorteDePelo);
			repositorioServicio.save(servicioBarberia);
			repositorioServicio.save(servicioAlisado);
			repositorioServicio.save(servicioTintura);
			repositorioServicio.save(servicioManicura);
			repositorioServicio.save(servicioPedicura);

			repositorioCliente.save(cliente1);
			repositorioCliente.save(admin);



		};
	}


}


