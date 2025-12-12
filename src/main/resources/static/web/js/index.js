var app = new Vue({
    el: "#app",
    data: {
        primerNombre: '',
        apellido: '',
        email: '',
        password: '',
        numeroTelefono: '',
        isSubmitting: false,
    },

    created() {
        this.verificarSiYaEstaLogeado();
    },

    methods: {
        verificarSiYaEstaLogeado() {
            axios.get('/api/cliente/current')
                .then(response => {
                    // Ya está logeado, verificar si tiene carrito pendiente
                    const carritoProductos = localStorage.getItem('carrito-productos-vue');
                    const carritoServicios = localStorage.getItem('carrito-servicios-vue');
                    
                    const tieneProductos = carritoProductos && JSON.parse(carritoProductos).length > 0;
                    const tieneServicios = carritoServicios && JSON.parse(carritoServicios).length > 0;
                    
                    if (tieneProductos || tieneServicios) {
                        // Tiene productos/servicios en el carrito
                        let mensaje = 'Tienes ';
                        if (tieneProductos && tieneServicios) {
                            mensaje += 'productos y servicios en tu carrito';
                        } else if (tieneProductos) {
                            mensaje += 'productos en tu carrito';
                        } else {
                            mensaje += 'servicios en tu carrito';
                        }
                        
                        Swal.fire({
                            icon: 'question',
                            title: mensaje,
                            text: '¿Quieres continuar con tu compra?',
                            showCancelButton: true,
                            confirmButtonText: 'Sí, ver carrito',
                            cancelButtonText: 'No, ir a inicio',
                            confirmButtonColor: '#007bff'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                // Redirigir según qué tipo de carrito tenga
                                if (tieneProductos) {
                                    window.location.href = '/web/html/productos.html';
                                } else {
                                    window.location.href = '/web/html/servicios.html';
                                }
                            } else {
                                window.location.href = '/web/html/home.html';
                            }
                        });
                    } else {
                        // No tiene carrito, ir a home
                        window.location.href = '/web/html/home.html';
                    }
                })
                .catch(error => {
                    // No está logeado, quedarse en index
                    console.log('Usuario no logeado, mostrando formulario');
                });
        },

        iniciarSesion() {
            axios.post('/api/login', 
                "email=" + this.email + "&password=" + this.password, 
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => { 
                    // 🔧 GUARDAR DATOS DEL USUARIO EN LOCALSTORAGE
                    const userData = {
                        email: this.email,
                        rol: this.email.includes('@admin') ? 'admin' : 'cliente'
                    };
                    localStorage.setItem('user', JSON.stringify(userData));
                    
                    // 🛒 Verificar si tiene carrito pendiente (productos o servicios)
                    const carritoProductos = localStorage.getItem('carrito-productos-vue');
                    const carritoServicios = localStorage.getItem('carrito-servicios-vue');
                    
                    const tieneProductos = carritoProductos && JSON.parse(carritoProductos).length > 0;
                    const tieneServicios = carritoServicios && JSON.parse(carritoServicios).length > 0;
                    
                    if (tieneProductos || tieneServicios) {
                        // Tiene productos/servicios, redirigir al carrito correspondiente
                        Swal.fire({
                            icon: 'success',
                            title: 'Sesión iniciada',
                            text: 'Continúa con tu compra',
                            showConfirmButton: false,
                            timer: 1500
                        });
                        setTimeout(() => {
                            // Priorizar productos si tiene ambos
                            if (tieneProductos) {
                                window.location.href = "/web/html/productos.html";
                            } else {
                                window.location.href = "/web/html/servicios.html";
                            }
                        }, 1500);
                    } else {
                        // No tiene carrito, ir a home
                        window.location.href = "/web/html/home.html";
                    }
                })
                .catch(error => {
                    Swal.fire({
                        title: 'Error',
                        text: 'Email o contraseña incorrecta. Vuelve a intentarlo.',
                        icon: "error",
                        showConfirmButton: false,
                        timer: 2200,
                    });
                });
        },

        normalizePhoneForSubmit() {
            return String(this.numeroTelefono || '').replace(/\D/g, '').slice(0, 11);
        },

        registrarse() {
            if (this.isSubmitting) return;
    
            const nameRegex = /^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{2,}$/;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const passRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$/;
            const phoneRegex = /^\d{11}$/;
    
            const nombreOk = this.primerNombre && nameRegex.test(this.primerNombre.trim());
            const apellidoOk = this.apellido && nameRegex.test(this.apellido.trim());
            const emailOk = this.email && emailRegex.test(this.email.trim());
            const passOk = this.password && passRegex.test(this.password);
            const digitsOnly = String(this.numeroTelefono || '').replace(/\D/g, '');
            const telefonoOk = phoneRegex.test(digitsOnly);
    
            if (!nombreOk) {
                Swal.fire({ icon: 'error', title: 'Nombre inválido', text: 'Ingresa un nombre válido (solo letras, mínimo 2 caracteres).', showConfirmButton: false, timer: 2500 });
                return;
            }
            if (!apellidoOk) {
                Swal.fire({ icon: 'error', title: 'Apellido inválido', text: 'Ingresa un apellido válido (solo letras, mínimo 2 caracteres).', showConfirmButton: false, timer: 2500 });
                return;
            }
            if (!emailOk) {
                Swal.fire({ icon: 'error', title: 'Email inválido', text: 'Ingresa un correo con formato válido.', showConfirmButton: false, timer: 2500 });
                return;
            }
            if (!passOk) {
                Swal.fire({ icon: 'error', title: 'Contraseña débil', text: 'La contraseña debe tener mínimo 8 caracteres e incluir letras y números.', showConfirmButton: false, timer: 3000 });
                return;
            }
            if (!telefonoOk) {
                Swal.fire({ icon: 'error', title: 'Teléfono inválido', text: "Ingresa 11 dígitos (ej. 11515151515).", showConfirmButton: false, timer: 3000 });
                return;
            }
    
            const numeroTelParaEnviar = this.normalizePhoneForSubmit();
    
            const payload = `primerNombre=${encodeURIComponent(this.primerNombre.trim())}&apellido=${encodeURIComponent(this.apellido.trim())}&email=${encodeURIComponent(this.email.trim())}&password=${encodeURIComponent(this.password)}&numeroTel=${encodeURIComponent(numeroTelParaEnviar)}`;
    
            this.isSubmitting = true;
            axios.post('/api/clientes', payload, 
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    this.isSubmitting = false;
                    Swal.fire({
                        icon: 'success',
                        title: 'Te registraste correctamente!',
                        showConfirmButton: false,
                        timer: 2000
                    });
                    setTimeout(() => { this.iniciarSesion(); }, 2100);
                })
                .catch(error => {
                    this.isSubmitting = false;
                    console.log(error);
                    if (error.response && error.response.status === 403) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Email ya registrado',
                            text: 'El correo ya existe. Usa otro email o recupera la contraseña.',
                            showConfirmButton: false,
                            timer: 3000
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'No se pudo registrar. Intenta más tarde.',
                            showConfirmButton: false,
                            timer: 3000
                        });
                    }
                });
        },
    }
});