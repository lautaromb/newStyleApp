// auth-check-optional.js
(function() {
    // Este script NO redirige, solo verifica si hay sesión
    window.usuarioAutenticado = false;
    window.esAdmin = false;
    window.datosUsuario = null;

    axios.get('/api/cliente/current')
        .then(response => {
            window.usuarioAutenticado = true;
            window.datosUsuario = response.data;
            window.esAdmin = response.data.email.includes('@admin.com');
            console.log('✅ Usuario autenticado:', response.data.email);
            
            // Disparar evento personalizado para que Vue lo detecte
            window.dispatchEvent(new Event('usuario-cargado'));
        })
        .catch(error => {
            window.usuarioAutenticado = false;
            window.esAdmin = false;
            console.log('ℹ️ Usuario no autenticado (modo invitado)');
            
            // Disparar evento para que Vue sepa que no hay usuario
            window.dispatchEvent(new Event('usuario-cargado'));
        });
})();