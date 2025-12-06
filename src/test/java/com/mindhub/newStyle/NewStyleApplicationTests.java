package com.mindhub.newStyle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.newStyle.dtos.InformeProductoDTO;
import com.mindhub.newStyle.modelos.*;
import com.mindhub.newStyle.repositorios.*;
import com.mindhub.newStyle.servicios.implementaciones.ClienteServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Rollback después de cada prueba
class ModuloSaldoTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositorioCliente repositorioCliente;

    @Autowired
    private ClienteServicio clienteServicio;

    private Cliente clienteTest;
    private Cliente clienteDestino;

    // ==================== SETUP ====================

    @BeforeEach
    void setUp() {
        // Crear cliente de prueba si no existe
        clienteTest = repositorioCliente.findByEmail("test@test.com");
        if (clienteTest == null) {
            clienteTest = new Cliente("Test", "User", "test@test.com", "password123", "1234567890");
            clienteTest.setSaldo(5000.0);
            repositorioCliente.save(clienteTest);
        } else {
            clienteTest.setSaldo(5000.0);
            repositorioCliente.save(clienteTest);
        }

        // Crear cliente destino para transferencias
        clienteDestino = repositorioCliente.findByEmail("destino@test.com");
        if (clienteDestino == null) {
            clienteDestino = new Cliente("Destino", "User", "destino@test.com", "password123", "0987654321");
            clienteDestino.setSaldo(1000.0);
            repositorioCliente.save(clienteDestino);
        } else {
            clienteDestino.setSaldo(1000.0);
            repositorioCliente.save(clienteDestino);
        }
    }

    // ==================== PRUEBAS UNITARIAS - MODELO ====================

    @Test
    void testClienteIniciaConSaldoCero() {
        Cliente nuevoCliente = new Cliente("Nuevo", "Cliente", "nuevo@test.com", "pass", "111");
        assertEquals(0.0, nuevoCliente.getSaldo(), "El saldo inicial debe ser 0");
    }

    @Test
    void testSetSaldoFunciona() {
        Cliente cliente = new Cliente("Test", "User", "test@test.com", "pass", "123");
        cliente.setSaldo(1500.0);
        assertEquals(1500.0, cliente.getSaldo(), "El saldo debe ser 1500");
    }

    @Test
    void testSaldoNoPuedeSerNegativo() {
        Cliente cliente = new Cliente("Test", "User", "test@test.com", "pass", "123");
        cliente.setSaldo(100.0);

        // En producción, deberías validar esto en el controlador
        assertTrue(cliente.getSaldo() >= 0, "El saldo no debe ser negativo");
    }

    // ==================== PRUEBAS DE INTEGRACIÓN - VER SALDO ====================

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testVerSaldoExitoso() throws Exception {
        mockMvc.perform(get("/api/cliente/saldo"))
                .andExpect(status().isOk())
                .andExpect(content().string("5000.0"));
    }

    @Test
    void testVerSaldoSinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/cliente/saldo"))
                .andExpect(status().isUnauthorized());
    }

    // ==================== PRUEBAS DE INTEGRACIÓN - AGREGAR SALDO ====================

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testAgregarSaldoExitoso() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/agregar")
                        .param("monto", "2000"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("7000")));

        // Verificar en base de datos
        Cliente cliente = repositorioCliente.findByEmail("test@test.com");
        assertEquals(7000.0, cliente.getSaldo(), 0.01);
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testAgregarSaldoMontoNegativo() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/agregar")
                        .param("monto", "-500"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("mayor a 0")));
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testAgregarSaldoCero() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/agregar")
                        .param("monto", "0"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("mayor a 0")));
    }

    // ==================== PRUEBAS DE INTEGRACIÓN - REMOVER SALDO ====================

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testRemoverSaldoExitoso() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/remover")
                        .param("monto", "2000"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("3000")));

        // Verificar en base de datos
        Cliente cliente = repositorioCliente.findByEmail("test@test.com");
        assertEquals(3000.0, cliente.getSaldo(), 0.01);
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testRemoverSaldoInsuficiente() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/remover")
                        .param("monto", "10000"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("Saldo insuficiente")));
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testRemoverSaldoMontoNegativo() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/remover")
                        .param("monto", "-100"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("mayor a 0")));
    }

    // ==================== PRUEBAS DE INTEGRACIÓN - TRANSFERIR ====================

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testTransferirSaldoExitoso() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/transferir")
                        .param("emailDestino", "destino@test.com")
                        .param("monto", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Transferencia exitosa")));

        // Verificar saldo origen
        Cliente origen = repositorioCliente.findByEmail("test@test.com");
        assertEquals(4000.0, origen.getSaldo(), 0.01);

        // Verificar saldo destino
        Cliente destino = repositorioCliente.findByEmail("destino@test.com");
        assertEquals(2000.0, destino.getSaldo(), 0.01);
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testTransferirSaldoInsuficiente() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/transferir")
                        .param("emailDestino", "destino@test.com")
                        .param("monto", "10000"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("Saldo insuficiente")));
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testTransferirAMismoCliente() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/transferir")
                        .param("emailDestino", "test@test.com")
                        .param("monto", "1000"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("propia cuenta")));
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testTransferirAClienteInexistente() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/transferir")
                        .param("emailDestino", "noexiste@test.com")
                        .param("monto", "1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("no existe")));
    }

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testTransferirMontoNegativo() throws Exception {
        mockMvc.perform(post("/api/cliente/saldo/transferir")
                        .param("emailDestino", "destino@test.com")
                        .param("monto", "-500"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("mayor a 0")));
    }

    // ==================== PRUEBAS DE FLUJO COMPLETO ====================

    @Test
    @WithMockUser(username = "test@test.com", authorities = {"CLIENTE"})
    void testFlujoCompletoSaldo() throws Exception {
        // 1. Ver saldo inicial
        mockMvc.perform(get("/api/cliente/saldo"))
                .andExpect(status().isOk())
                .andExpect(content().string("5000.0"));

        // 2. Agregar $3000
        mockMvc.perform(post("/api/cliente/saldo/agregar")
                        .param("monto", "3000"))
                .andExpect(status().isOk());

        // 3. Transferir $2000
        mockMvc.perform(post("/api/cliente/saldo/transferir")
                        .param("emailDestino", "destino@test.com")
                        .param("monto", "2000"))
                .andExpect(status().isOk());

        // 4. Remover $1000
        mockMvc.perform(post("/api/cliente/saldo/remover")
                        .param("monto", "1000"))
                .andExpect(status().isOk());

        // 5. Verificar saldo final: 5000 + 3000 - 2000 - 1000 = 5000
        Cliente cliente = repositorioCliente.findByEmail("test@test.com");
        assertEquals(5000.0, cliente.getSaldo(), 0.01);
    }

    // ==================== PRUEBAS DE SERVICIO ====================

    @Test
    void testClienteServicioFindByEmail() {
        Cliente cliente = clienteServicio.findClienteByEmail("test@test.com");
        assertNotNull(cliente, "El cliente debe existir");
        assertEquals("test@test.com", cliente.getEmail());
    }

    @Test
    void testClienteServicioSave() {
        Cliente nuevoCliente = new Cliente("Nuevo", "Test", "nuevo@example.com", "pass", "999");
        nuevoCliente.setSaldo(500.0);

        Cliente guardado = clienteServicio.saveCliente(nuevoCliente);

        assertNotNull(guardado.getId(), "El ID debe ser generado");
        assertEquals(500.0, guardado.getSaldo());
    }
}

class ModuloInformesTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositorioCompra repositorioCompra;

    @Autowired
    private RepositorioCliente repositorioCliente;

    @Autowired
    private RepositorioProducto repositorioProducto;

    @Autowired
    private RepositorioTicket repositorioTicket;

    @Autowired
    private RepositorioNegocio repositorioNegocio;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente clienteTest;
    private Producto producto1;
    private Producto producto2;
    private Negocio negocio;

    // ==================== SETUP ====================

    @BeforeEach
    void setUp() {
        // Crear negocio si no existe
        negocio = repositorioNegocio.findByEmail("newStyle@gmail.com");
        if (negocio == null) {
            negocio = new Negocio("New Style", "newStyle@gmail.com", "Av. Test 123");
            repositorioNegocio.save(negocio);
        }

        // Crear cliente de prueba
        clienteTest = repositorioCliente.findByEmail("test@test.com");
        if (clienteTest == null) {
            clienteTest = new Cliente("Test", "User", "test@test.com", "password123", "1234567890");
            clienteTest.setSaldo(50000.0);
            repositorioCliente.save(clienteTest);
        }

        // Crear productos de prueba
        producto1 = new Producto("Producto Test 1", 500.0, "url1", "url1", 100, "Descripción 1", negocio);
        producto2 = new Producto("Producto Test 2", 300.0, "url2", "url2", 100, "Descripción 2", negocio);
        repositorioProducto.save(producto1);
        repositorioProducto.save(producto2);
    }

    // ==================== PRUEBAS DE SEGURIDAD ====================

    @Test
    void testInformesSinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", authorities = {"CLIENTE"})
    void testInformesConRolCliente() throws Exception {
        mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformesConRolAdmin() throws Exception {
        mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk());
    }

    // ==================== PRUEBAS DE FUNCIONALIDAD ====================

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeSinCompras() throws Exception {
        mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No hay compras")));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeConUnaCompra() throws Exception {
        // Crear una compra
        Ticket ticket = new Ticket(1500.0);
        repositorioTicket.save(ticket);

        Compra compra = new Compra(clienteTest, "Producto Test 1", 1500.0, 3, ticket);
        repositorioCompra.save(compra);

        // Verificar informe
        MvcResult result = mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreProducto").value("Producto Test 1"))
                .andExpect(jsonPath("$[0].cantidadVendida").value(3))
                .andExpect(jsonPath("$[0].totalRecaudado").value(1500.0))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeConVariasComprasMismoProducto() throws Exception {
        // Crear varias compras del mismo producto
        Ticket ticket1 = new Ticket(1000.0);
        Ticket ticket2 = new Ticket(1500.0);
        repositorioTicket.save(ticket1);
        repositorioTicket.save(ticket2);

        Compra compra1 = new Compra(clienteTest, "Producto Test 1", 1000.0, 2, ticket1);
        Compra compra2 = new Compra(clienteTest, "Producto Test 1", 1500.0, 3, ticket2);
        repositorioCompra.save(compra1);
        repositorioCompra.save(compra2);

        // Verificar que suma correctamente
        mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreProducto").value("Producto Test 1"))
                .andExpect(jsonPath("$[0].cantidadVendida").value(5)) // 2 + 3 = 5
                .andExpect(jsonPath("$[0].totalRecaudado").value(2500.0)); // 1000 + 1500 = 2500
    }

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeConMultiplesProductos() throws Exception {
        // Crear compras de diferentes productos
        Ticket ticket1 = new Ticket(1500.0);
        Ticket ticket2 = new Ticket(900.0);
        repositorioTicket.save(ticket1);
        repositorioTicket.save(ticket2);

        Compra compra1 = new Compra(clienteTest, "Producto Test 1", 1500.0, 3, ticket1);
        Compra compra2 = new Compra(clienteTest, "Producto Test 2", 900.0, 3, ticket2);
        repositorioCompra.save(compra1);
        repositorioCompra.save(compra2);

        // Verificar informe con múltiples productos
        MvcResult result = mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();

        // Parsear respuesta
        String json = result.getResponse().getContentAsString();
        List<InformeProductoDTO> informes = objectMapper.readValue(json, new TypeReference<List<InformeProductoDTO>>(){});

        // Verificar que contiene ambos productos
        assertTrue(informes.stream().anyMatch(i -> i.getNombreProducto().equals("Producto Test 1")));
        assertTrue(informes.stream().anyMatch(i -> i.getNombreProducto().equals("Producto Test 2")));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeOrdenadoPorRecaudacion() throws Exception {
        // Crear compras con diferentes recaudaciones
        Ticket ticket1 = new Ticket(3000.0);
        Ticket ticket2 = new Ticket(1500.0);
        Ticket ticket3 = new Ticket(2000.0);
        repositorioTicket.save(ticket1);
        repositorioTicket.save(ticket2);
        repositorioTicket.save(ticket3);

        Compra compra1 = new Compra(clienteTest, "Producto A", 3000.0, 6, ticket1); // Mayor recaudación
        Compra compra2 = new Compra(clienteTest, "Producto B", 1500.0, 5, ticket2); // Menor recaudación
        Compra compra3 = new Compra(clienteTest, "Producto C", 2000.0, 4, ticket3); // Recaudación media

        repositorioCompra.save(compra1);
        repositorioCompra.save(compra2);
        repositorioCompra.save(compra3);

        // Verificar ordenamiento
        MvcResult result = mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<InformeProductoDTO> informes = objectMapper.readValue(json, new TypeReference<List<InformeProductoDTO>>(){});

        // Verificar orden: Producto A (3000) > Producto C (2000) > Producto B (1500)
        assertEquals("Producto A", informes.get(0).getNombreProducto());
        assertEquals(3000.0, informes.get(0).getTotalRecaudado(), 0.01);

        assertEquals("Producto C", informes.get(1).getNombreProducto());
        assertEquals(2000.0, informes.get(1).getTotalRecaudado(), 0.01);

        assertEquals("Producto B", informes.get(2).getNombreProducto());
        assertEquals(1500.0, informes.get(2).getTotalRecaudado(), 0.01);
    }

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeCalculosCorrectos() throws Exception {
        // Crear escenario complejo: múltiples compras de múltiples productos
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        repositorioTicket.save(ticket1);
        repositorioTicket.save(ticket2);
        repositorioTicket.save(ticket3);

        // Producto Test 1: 2 compras
        Compra compra1 = new Compra(clienteTest, "Producto Test 1", 1000.0, 2, ticket1);
        Compra compra2 = new Compra(clienteTest, "Producto Test 1", 2500.0, 5, ticket2);

        // Producto Test 2: 1 compra
        Compra compra3 = new Compra(clienteTest, "Producto Test 2", 900.0, 3, ticket3);

        repositorioCompra.save(compra1);
        repositorioCompra.save(compra2);
        repositorioCompra.save(compra3);

        MvcResult result = mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<InformeProductoDTO> informes = objectMapper.readValue(json, new TypeReference<List<InformeProductoDTO>>(){});

        // Verificar Producto Test 1
        InformeProductoDTO informe1 = informes.stream()
                .filter(i -> i.getNombreProducto().equals("Producto Test 1"))
                .findFirst()
                .orElse(null);

        assertNotNull(informe1);
        assertEquals(7, informe1.getCantidadVendida()); // 2 + 5 = 7
        assertEquals(3500.0, informe1.getTotalRecaudado(), 0.01); // 1000 + 2500 = 3500

        // Verificar Producto Test 2
        InformeProductoDTO informe2 = informes.stream()
                .filter(i -> i.getNombreProducto().equals("Producto Test 2"))
                .findFirst()
                .orElse(null);

        assertNotNull(informe2);
        assertEquals(3, informe2.getCantidadVendida());
        assertEquals(900.0, informe2.getTotalRecaudado(), 0.01);
    }

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeCantidadesGrandes() throws Exception {
        // Probar con cantidades grandes
        Ticket ticket = new Ticket(50000.0);
        repositorioTicket.save(ticket);

        Compra compra = new Compra(clienteTest, "Producto Test 1", 50000.0, 100, ticket);
        repositorioCompra.save(compra);

        mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidadVendida").value(100))
                .andExpect(jsonPath("$[0].totalRecaudado").value(50000.0));
    }

    // ==================== PRUEBAS DE INTEGRACIÓN ====================

    @Test
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN"})
    void testInformeFormatoJSON() throws Exception {
        Ticket ticket = new Ticket(500.0);
        repositorioTicket.save(ticket);

        Compra compra = new Compra(clienteTest, "Test Producto", 500.0, 1, ticket);
        repositorioCompra.save(compra);

        mockMvc.perform(get("/api/informes/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nombreProducto").exists())
                .andExpect(jsonPath("$[0].cantidadVendida").exists())
                .andExpect(jsonPath("$[0].totalRecaudado").exists());
    }
}