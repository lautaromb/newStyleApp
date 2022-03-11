package com.mindhub.newStyle;

import com.mindhub.newStyle.models.Negocio;
import com.mindhub.newStyle.models.Sucursal;
import com.mindhub.newStyle.repositorios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewStyleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewStyleApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(RepositorioNegocio repositorioNegocio,
									  RepositorioSucursal repositorioSucursal,
									  RepositorioCliente repositorioCliente,
									  RepositorioProducto repositorioProducto,
									  RespositorioServicio respositorioServicio){
		return (args) ->{

			Negocio newStyle = new Negocio("New Style", "newStyle@gmail.com");

			Sucursal sucursalCaba = new Sucursal("New Style Caballito", "newStyleCaballito@gmail.com", "Av. Cordoba 2350", "8:00", "21:00", newStyle);
			Sucursal sucursalZO = new Sucursal("New Style Ramos Mejía", "newStyleRamosMejia@gmail.com", "Av. de Mayo 2042", "8:00", "21:00", newStyle);
			Sucursal sucursalZS = new Sucursal("New Style Adrogue", "newStyleAdrogue@gmail.com", "Av. Espora 2042", "8:00", "21:00", newStyle);


			repositorioNegocio.save(newStyle);
			repositorioSucursal.save(sucursalCaba);
			repositorioSucursal.save(sucursalZO);
			repositorioSucursal.save(sucursalZS);

		};
	}


}


