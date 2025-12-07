const LoginView = {
    data() {
        return {
            // Login
            emailLogin: '',
            passwordLogin: '',
            
            // Registro
            primerNombre: '',
            apellido: '',
            emailRegistro: '',
            passwordRegistro: '',
            numeroTelefono: '',
            
            isSubmitting: false,
            showLogin: true // true = login, false = registro
        };
    },
    template: `
        <div class="container" style="min-height: 80vh;">
            <input type="checkbox" id="flip" v-model="showLogin">
            <div class="cover">
                <div class="front">
                    <img src="assets/mujer.jpg" alt="">
                </div>
                <div class="back">
                    <img class="backImg" src="assets/mujer2.jpg" alt="">
                </div>
            </div>
            <div class="formulario">
                <div class="formulario-contenido">
                    <!-- FORMULARIO DE LOGIN -->
                    <div class="formulario-ingreso" v-show="showLogin">
                        <div class="titulo">Entrar</div>
                        <form @submit.prevent="iniciarSesion">
                            <div class="input-boxes">
                                <div class="input-box">
                                    <i class="fas fa-envelope"></i>
                                    <input type="email" placeholder="Correo Electrónico" v-model="emailLogin" required>
                                </div>
                                <div class="input-box">
                                    <i class="fas fa-lock"></i>
                                    <input type="password" placeholder="Contraseña" v-model="passwordLogin" required>
                                </div>
                                <div class="text"><a href="#">¿Has olvidado la contraseña?</a></div>
                                <div class="boton input-box">
                                    <input type="submit" value="Entrar">
                                </div>
                                <div class="text texto-registrarse">
                                    ¿No tienes una cuenta? 
                                    <label @click="showLogin = false" style="cursor: pointer;">Regístrate</label>
                                </div>
                            </div>
                        </form>
                    </div>
                    
                    <!-- FORMULARIO DE REGISTRO -->
                    <div class="registrate" v-show="!showLogin">
                        <div class="titulo">Registrarte</div>
                        <form @submit.prevent="registrarse">
                            <div class="input-boxes">
                                <div class="input-box">
                                    <i class="fas fa-user"></i>
                                    <input type="text" placeholder="Nombre" v-model="primerNombre" required
                                           pattern="[A-Za-zÁÉÍÓÚÑáéíóúñ ]{2,}" 
                                           title="Mínimo 2 letras, solo letras y espacios" maxlength="50">
                                </div>
                                <div class="input-box">
                                    <i class="fas fa-user"></i>
                                    <input type="text" placeholder="Apellido" v-model="apellido" required
                                           pattern="[A-Za-zÁÉÍÓÚÑáéíóúñ ]{2,}" 
                                           title="Mínimo 2 letras, solo letras y espacios" maxlength="50">
                                </div>
                                <div class="input-box">
                                    <i class="fas fa-envelope"></i>
                                    <input type="email" placeholder="Correo Electrónico" v-model="emailRegistro" required
                                           title="Introduce un correo válido (ej. usuario@dominio.com)">
                                </div>
                                <div class="input-box">
                                    <i class="fas fa-lock"></i>
                                    <input type="password" placeholder="Contraseña" v-model="passwordRegistro" required
                                           minlength="8" pattern="(?=.*[A-Za-z])(?=.*\\d).{8,}" 
                                           title="Mínimo 8 caracteres, debe incluir letras y números">
                                </div>
                                <div class="input-box">
                                    <i class="fas fa-phone"></i>
                                    <input type="tel" placeholder="Ej: 11515151515" v-model="numeroTelefono" required
                                           pattern="^\\d{11}$" title="Ingresa 11 dígitos (ej. 11515151515)"
                                           inputmode="numeric" maxlength="11" />
                                </div>
                                <div class="boton input-box">
                                    <input type="submit" value="Registrarte" :disabled="isSubmitting">
                                </div>
                                <div class="text texto-registrarse">
                                    ¿Tienes una cuenta? 
                                    <label @click="showLogin = true" style="cursor: pointer;">Entrar</label>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    `,
    methods: {
        iniciarSesion() {
            const payload = `email=${this.emailLogin}&password=${this.passwordLogin}`;
            
            axios.post('/api/login', payload, {
                headers: { 'content-type': 'application/x-www-form-urlencoded' }
            })
            .then(response => {
                // Cargar usuario
                return axios.get('/api/cliente/current');
            })
            .then(response => {
                store.state.usuario = response.data;
                
                Swal.fire({
                    icon: 'success',
                    title: '¡Bienvenido!',
                    text: 'Has iniciado sesión correctamente',
                    showConfirmButton: false,
                    timer: 1500
                });
                
                this.$router.push('/');
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
        
        registrarse() {
            if (this.isSubmitting) return;
            
            const nameRegex = /^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{2,}$/;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const passRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$/;
            const phoneRegex = /^\d{11}$/;
            
            const nombreOk = this.primerNombre && nameRegex.test(this.primerNombre.trim());
            const apellidoOk = this.apellido && nameRegex.test(this.apellido.trim());
            const emailOk = this.emailRegistro && emailRegex.test(this.emailRegistro.trim());
            const passOk = this.passwordRegistro && passRegex.test(this.passwordRegistro);
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
            
            const payload = `primerNombre=${encodeURIComponent(this.primerNombre.trim())}&apellido=${encodeURIComponent(this.apellido.trim())}&email=${encodeURIComponent(this.emailRegistro.trim())}&password=${encodeURIComponent(this.passwordRegistro)}&numeroTel=${encodeURIComponent(digitsOnly)}`;
            
            this.isSubmitting = true;
            axios.post('/api/clientes', payload, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    this.isSubmitting = false;
                    Swal.fire({
                        icon: 'success',
                        title: 'Te registraste correctamente!',
                        showConfirmButton: false,
                        timer: 2000
                    });
                    setTimeout(() => { 
                        this.emailLogin = this.emailRegistro;
                        this.passwordLogin = this.passwordRegistro;
                        this.iniciarSesion(); 
                    }, 2100);
                })
                .catch(error => {
                    this.isSubmitting = false;
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
        }
    }
};