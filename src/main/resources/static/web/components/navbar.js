/**
 * Navbar Component - NewStyle
 * Sistema de navegación + carrito conectado a Vue.
 */

(function () {
    'use strict';

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initNavbar);
    } else {
        initNavbar();
    }

    function initNavbar() {
        insertNavbarHTML();
        setupMobileMenu();
        setupCart();
        checkAuthStatus();
        highlightCurrentPage();
    }

    /**
     * Inserta el navbar en el body
     */
    function insertNavbarHTML() {
        const navbarHTML = `
<header>
    <nav>
        <div class="logo">New Style</div>

        <input type="checkbox" id="click">
        <label for="click" class="menu-btn">
            <i class="fas fa-bars"></i>
        </label>

<ul>

    <!-- PÚBLICO -->
    <li><a href="home.html" data-page="home">Inicio</a></li>
    <li><a href="productos.html" data-page="productos">Productos</a></li>

    <!-- ADMIN (solo admin) -->
    <li class="admin-only" style="display:none;">
        <a href="gestionProductos.html" data-page="gestionProductos">Gestión Productos</a>
    </li>
    <li class="admin-only" style="display:none;">
        <a href="informes.html" data-page="informes">Informes</a>
    </li>
    <li class="admin-only" style="display:none;">
        <a href="despacho.html" data-page="despacho">Despacho</a>
    </li>

    <!-- USUARIO (logueado + NO admin) -->
    <li class="user-only" style="display:none;">
        <a href="historialCompras.html" data-page="historialCompras">Mis Compras</a>
    </li>
    <li class="user-only" style="display:none;">
        <a href="saldo.html" data-page="saldo">Mi Saldo</a>
    </li>
    <li class="user-only" style="display:none;">
        <a href="#" id="logoutBtn">Salir</a>
    </li>

    <!-- VISITANTE -->
    <li class="guest-only">
        <a href="index.html">Iniciar Sesión</a>
    </li>

    <!-- CARRITO SOLO PARA USUARIO COMÚN -->
    <li class="user-only" style="display:none;">
        <div id="carritoBox" style="display:flex; align-items:center; gap:5px; cursor:pointer;">
            <box-icon name="cart" color="#ffffff" id="cartIcon"></box-icon>
            <span id="cartCount" class="carrito" style="color:white; font-weight:bold;">0</span>
        </div>
    </li>

</ul>

    </nav>
</header>

`;

        document.body.insertAdjacentHTML('afterbegin', navbarHTML);
    }

    /**
     * Menú hamburguesa
     */
    function setupMobileMenu() {
        const menuButton = document.getElementById('click');
        const ul = document.querySelector('nav ul');
        if (!menuButton || !ul) return;

        menuButton.addEventListener('change', () => {
            ul.classList.toggle('active');
        });
    }

    /**
     * 🔥 Carrito: integración con Vue
     */
    function setupCart() {
        const cartIcon = document.getElementById("cartIcon");
        const cartBox = document.getElementById("carritoBox");

        if (!cartIcon || !cartBox) return;

        cartBox.addEventListener("click", () => {
            // Ejecutar calcular() si Vue ya existe
            if (window.vueApp && typeof window.vueApp.calcular === "function") {
                window.vueApp.calcular();
            }

            // Abrir modal (Bootstrap 5)
            const modal = document.getElementById("modal");
            if (modal && bootstrap) {
                const modalInstance = new bootstrap.Modal(modal);
                modalInstance.show();
            }
        });
    }

    /** 
     * 🔥 Función global para actualizar el contador 
     * (Vue la llama cada vez que cambia el carrito)
     */
    window.updateCartCount = function (count) {
        const cartCount = document.getElementById("cartCount");
        if (cartCount) cartCount.textContent = count;
    };

    /**
     * Estado del usuario
     */
    function checkAuthStatus() {
        const user = JSON.parse(localStorage.getItem('user'));

        if (user) {
            updateNavbarForUser(user);
        } else {
            updateNavbarForGuest();
        }

        const logoutBtn = document.getElementById('logoutBtn');
        if (logoutBtn) {
            logoutBtn.addEventListener('click', handleLogout);
        }
    }

    function updateNavbarForUser(user) {

        // Ocultamos todo por defecto
        document.querySelectorAll('.guest-only').forEach(el => el.style.display = 'none');
        document.querySelectorAll('.auth-only').forEach(el => el.style.display = 'none');
        document.querySelectorAll('.admin-only').forEach(el => el.style.display = 'none');
        document.querySelectorAll('.user-only').forEach(el => el.style.display = 'none'); // nuevo

        if (user.rol === 'admin') {

            // SOLO ADMIN
            document.querySelectorAll('.admin-only').forEach(el => el.style.display = 'flex');

        } else {

            // USUARIO NORMAL
            document.querySelectorAll('.auth-only').forEach(el => el.style.display = 'flex');
            document.querySelectorAll('.user-only').forEach(el => el.style.display = 'flex');
        }
    }

    function updateNavbarForGuest() {
        document.querySelectorAll('.guest-only').forEach(el => el.style.display = 'flex');
        document.querySelectorAll('.auth-only').forEach(el => el.style.display = 'none');
        document.querySelectorAll('.admin-only').forEach(el => el.style.display = 'none');
        document.querySelectorAll('.user-only').forEach(el => el.style.display = 'none');
    }


    /**
     * Resaltar página actual
     */
    function highlightCurrentPage() {
        const currentPage = window.location.pathname.split('/').pop().replace('.html', '');
        const links = document.querySelectorAll('[data-page]');

        links.forEach(link => {
            if (link.getAttribute('data-page') === currentPage) {
                link.classList.add('active');
            }
        });
    }

    /**
     * Logout
     */
    function handleLogout(e) {
        e.preventDefault();

        // Hacer logout en el backend
        axios.post('/api/logout')
            .then(() => {
                localStorage.clear();

                window.location.href = 'index.html';
            })
            .catch(error => {
                console.error('Error en logout:', error);

                localStorage.clear();
                window.location.href = 'index.html';
            });
    }

})();
