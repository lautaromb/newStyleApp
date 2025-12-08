// Navbar component
document.addEventListener('DOMContentLoaded', function() {
    // Create navbar HTML
    const navbarHTML = `
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="../js/auth-check.js"></script>
    <nav class="navbar">
        <div class="navbar-container">
            <a href="index.html" class="navbar-logo">
                <span>NewStyle</span>
            </a>
            <div class="menu-icon" onclick="toggleMenu()">
                <i class="fas fa-bars"></i>
            </div>
            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="index.html" class="nav-link">Inicio</a>
                </li>
                <li class="nav-item">
                    <a href="servicios.html" class="nav-link">Servicios</a>
                </li>
                <li class="nav-item">
                    <a href="productos.html" class="nav-link">Productos</a>
                </li>
                <li class="nav-item" id="adminLink1" style="display: none;">
                    <a href="formularioServicio.html" class="nav-link">Crear Servicio</a>
                </li>
                <li class="nav-item" id="adminLink2" style="display: none;">
                    <a href="formularioProducto.html" class="nav-link">Crear Producto</a>
                </li>
                <li class="nav-item" id="adminLink3" style="display: none;">
                    <a href="informes.html" class="nav-link">Informes</a>
                </li>
                <li class="nav-item" id="adminLink4" style="display: none;">
                    <a href="despacho.html" class="nav-link">Despacho</a>
                </li>
                <li class="nav-item">
                    <a href="contacto.html" class="nav-link">Contacto</a>
                </li>
                <li class="nav-item">
                    <a href="login.html" class="nav-link login-btn" id="loginBtn">Iniciar Sesión</a>
                </li>
                <li class="nav-item" id="saldoLink" style="display: none;">
                    <a href="saldo.html" class="nav-link">Mi Saldo</a>
                </li>
                <li class="nav-item" id="logoutBtn" style="display: none;">
                    <a href="#" class="nav-link" onclick="logout()">Cerrar Sesión</a>
                </li>
            </ul>
        </div>
    </nav>`;

    // Function to toggle mobile menu
    window.toggleMenu = function() {
        const navMenu = document.querySelector('.nav-menu');
        navMenu.classList.toggle('active');
    };

    // Function to check admin status
    function checkAdminStatus() {
        const user = JSON.parse(localStorage.getItem('user'));
        const adminLinks = document.querySelectorAll('[id^="adminLink"]');
        const loginBtn = document.getElementById('loginBtn');
        const logoutBtn = document.getElementById('logoutBtn');
        const saldoLink = document.getElementById('saldoLink');

        if (user) {
            // User is logged in
            if (loginBtn) loginBtn.style.display = 'none';
            if (logoutBtn) logoutBtn.style.display = 'block';
            
            // Show saldo link for all logged-in users
            if (saldoLink) saldoLink.style.display = 'block';

            // Check if user is admin
            if (user.rol === 'admin') {
                adminLinks.forEach(link => {
                    link.style.display = 'block';
                });
            } else {
                adminLinks.forEach(link => {
                    link.style.display = 'none';
                });
            }
        } else {
            // User is not logged in
            if (loginBtn) loginBtn.style.display = 'block';
            if (logoutBtn) logoutBtn.style.display = 'none';
            if (saldoLink) saldoLink.style.display = 'none';
            adminLinks.forEach(link => {
                link.style.display = 'none';
            });
        }
    }

    // Logout function
    window.logout = function() {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    };

    // Insert navbar at the beginning of the body
    document.body.insertAdjacentHTML('afterbegin', navbarHTML);
    
    // Check admin status when page loads
    checkAdminStatus();
    
    // Also check admin status when auth state changes
    window.addEventListener('storage', function(event) {
        if (event.key === 'user') {
            checkAdminStatus();
        }
    });
});