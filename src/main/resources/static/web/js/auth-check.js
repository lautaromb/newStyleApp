// auth-check.js
function verificarAutenticacion() {
    axios.get('/api/cliente/current')
        .then(response => {
            // Usuario autenticado, continuar
            console.log('Usuario autenticado:', response.data.email);
        })
        .catch(error => {
            // No autenticado, redirigir al login
            console.log('Usuario no autenticado, redirigiendo...');
            window.location.href = '/web/html/index.html';
        });
}

// Verificar inmediatamente al cargar la página
verificarAutenticacion();