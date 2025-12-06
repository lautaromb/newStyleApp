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
									 ) {
        return (args) -> {

            if (repositorioNegocio.findByEmail("newStyle@gmail.com") == null) {

                Negocio newStyle = new Negocio("New Style", "newStyle@gmail.com", "Av. de Mayo 2859");
                repositorioNegocio.save(newStyle);

                // Clientes
                Cliente admin = new Cliente("Admin", "New Style", "admind@admin.com", passwordEncoder.encode("admin789"), "0000");
                Cliente cliente1 = new Cliente("User", "Resu", "user@gmail.com", passwordEncoder.encode("user123"), "0000");

                repositorioCliente.save(admin);
                repositorioCliente.save(cliente1);

                // Servicios
                Servicio servicioCorteDePelo = new Servicio("Corte de pelo", 400.0, "https://i.imgur.com/P3HUPFc.jpeg", "url aqui", "Corte a elección del cliente", newStyle);
                Servicio servicioTintura = new Servicio("Tintura", 700.0, "url", "url", "tintura", newStyle);
                Servicio servicioAlisado = new Servicio("Alisado", 1100.0, "url aqui", "url aqui", "Tratamiento de alisado", newStyle);
                Servicio servicioBarberia = new Servicio("Barba", 200.0, "url aqui", "url aqui", "Corte Aceitado", newStyle);
                Servicio servicioManicura = new Servicio("Manicura", 500.0, "url aqui", "url aqui", "Limpieza, esmaltado", newStyle);
                Servicio servicioPedicura = new Servicio("Pedicura", 500.0, "url aqui", "url aqui", "Limpieza y esmaltado", newStyle);
                Servicio servicioPestaña = new Servicio("Extension Pestaña", 800.0, "url aqui", "url aqui", "Extenciones de pestañas", newStyle);

                repositorioServicio.save(servicioCorteDePelo);
                repositorioServicio.save(servicioBarberia);
                repositorioServicio.save(servicioAlisado);
                repositorioServicio.save(servicioTintura);
                repositorioServicio.save(servicioManicura);
                repositorioServicio.save(servicioPedicura);
                repositorioServicio.save(servicioPestaña);

                // Productos
                Producto productoEnjuague = new Producto("Crema De Enjuague", 350.0, "url aqui", "url aqui", 8, "Recuperacion milagrosa para el cabello", newStyle);
                Producto productoTinturaRoja = new Producto("Tintura Roja", 350.0, "url aqui", "url aqui", 15, "Duracion extendida de 20 a 30 dias con lavado moderado agua tibia", newStyle);
                Producto productoTinturaAzul = new Producto("Tintura Azul", 350.0, "url aqui", "url aqui", 20, "Duracion extendida de 20 a 30 dias con lavado moderado agua tibia", newStyle);
                Producto productoKeratina = new Producto("Keratina", 500.0, "url aqui", "url aqui", 5, "Repara y nutre el cabello de raiz a las puntas", newStyle);
                Producto productoBotoxCabello = new Producto("Botox Cabello", 600.0, "url aqui", "url aqui", 10, "Restaruracion y brillo del cabello", newStyle);
                Producto productoAntiFrizz = new Producto("Keratina Anti Frizz", 800.0, "url aqui", "url aqui", 4, "Controla el cabello con frizz con los productos de peinado en la ducha", newStyle);

                repositorioProducto.save(productoEnjuague);
                repositorioProducto.save(productoTinturaRoja);
                repositorioProducto.save(productoTinturaAzul);
                repositorioProducto.save(productoKeratina);
                repositorioProducto.save(productoBotoxCabello);
                repositorioProducto.save(productoAntiFrizz);
            }
        };

    }
}


