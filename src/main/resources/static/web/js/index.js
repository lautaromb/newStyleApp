var app = new Vue({
    el: "#app",
    data: {
        primerNombre: '',
        apellido: '',
        email: '',
        password: '',
        numeroTelefono: '',
        isSubmitting: false,
        // teléfono ahora simple: solo un string con hasta 11 dígitos
        // no validaciones/flags extras en vivo
    },


    methods: {
      iniciarSesion() {
        axios.post('/api/login', "email=" + this.email + "&password=" + this.password, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
            .then(response => { window.location.href = "/web/html/home.html" })
            .catch(error => {

                Swal.fire({
                    title: 'Error',
                    text: 'Email o contraseña incorrecta. Vuelve a intentarlo.',
                    icon: "error",
                    showConfirmButton: false,
                    timer: 2200,
                })
            })
      },

      // Normaliza teléfono para envío: devuelve solo los dígitos (máx 11)
      normalizePhoneForSubmit() {
        return String(this.numeroTelefono || '').replace(/\D/g, '').slice(0, 11);
      },

      registrarse() {
        if (this.isSubmitting) return;
 
         const nameRegex = /^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{2,}$/;
         const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
         const passRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$/;
        // ahora acepta SOLO 11 dígitos
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
         axios.post('/api/clientes', payload, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
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
    } // end methods
}); // end Vue