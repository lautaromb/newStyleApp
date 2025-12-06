var app = new Vue({
    el: "#app",
    data: {
        primerNombre: '',
        apellido: '',
        email: '',
        password: '',
        numeroTelefono: '',
        isSubmitting: false,
        telefonoFormatoValido: true
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

      // formatea SOLO 10 dígitos como AA-NNNN-NNNN; elimina cualquier letra/char
      formatPhone() {
        // tomar solo dígitos y limitar a 10
        let digits = String(this.numeroTelefono || '').replace(/\D/g, '').slice(0, 10);
        const parts = [];
        if (digits.length > 0) parts.push(digits.slice(0, 2));
        if (digits.length > 2) parts.push(digits.slice(2, 6));
        if (digits.length > 6) parts.push(digits.slice(6, 10));
        this.numeroTelefono = parts.filter(Boolean).join('-');
        // no marcar error mientras el usuario está tipeando
        this.telefonoFormatoValido = true;
      },

      handlePastePhone(e) {
        setTimeout(() => this.formatPhone(), 0);
      },

      // valida el telefono al salir del campo
      validatePhoneOnBlur() {
        this.telefonoFormatoValido = this.isPhoneFormatValid(this.numeroTelefono);
      },

      // Normaliza y devuelve AA-NNNN-NNNN si es posible
      normalizePhoneForSubmit() {
        const s = String(this.numeroTelefono || '').trim();
        if (this.isPhoneFormatValid(s)) return s;
        const digits = s.replace(/\D/g, '');
        if (digits.length === 10) {
          return digits.slice(0,2) + '-' + digits.slice(2,6) + '-' + digits.slice(6,10);
        }
        return this.numeroTelefono;
      },

      registrarse() {
        if (this.isSubmitting) return;

        const nameRegex = /^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{2,}$/;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const passRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$/;
        // ahora acepta SOLO AA-NNNN-NNNN
        const phoneRegex = /^\d{2}-\d{4}-\d{4}$/;

        const nombreOk = this.primerNombre && nameRegex.test(this.primerNombre.trim());
        const apellidoOk = this.apellido && nameRegex.test(this.apellido.trim());
        const emailOk = this.email && emailRegex.test(this.email.trim());
        const passOk = this.password && passRegex.test(this.password);
        const telefonoOk = this.numeroTelefono && phoneRegex.test(String(this.numeroTelefono).trim());

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
          Swal.fire({ icon: 'error', title: 'Teléfono inválido', text: "Formato aceptado: '11-1111-1111'. Sólo dígitos y guiones en posiciones correctas.", showConfirmButton: false, timer: 3000 });
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

      // valida formato final aceptado: AA-NNNN-NNNN (solo dígitos)
      isPhoneFormatValid(phone) {
        const s = String(phone || '').trim();
        const pattern = /^\d{2}-\d{4}-\d{4}$/;
        if (!pattern.test(s)) return false;
        const digits = s.replace(/\D/g, '');
        return digits.length === 10;
      },
}