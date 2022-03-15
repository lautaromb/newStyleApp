var app = new Vue({
    el: "#app",
    data: {
        primerNombre: '',
        apellido: '',
        email: '',
        password: '',
        numeroTelefono: ''

    },


    methods: {
        iniciarSesion() {
            axios.post('/api/login', "email=" + this.email + "&password=" + this.password, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => { window.location.href = "/web/html/home.html" })
                .catch(error => {

                    Swal.fire({
                        title: 'Invalid combination',
                        text: 'Please re-enter your details',
                        icon: "error",
                        showConfirmButton: false,
                        timer: 2000,
                    })
                })
        },

        registrarse() {
            if (this.primerNombre != '' && this.apellido != '' && this.email != '' && this.password != '' && this.numeroTelefono != '') {
                Swal.fire({
                    title: 'Te registraste!',
                    text: `Bienvenido/a ${this.primerNombre.toUpperCase()} ${this.apellido.toUpperCase()}!`,
                    icon: "success",
                    showConfirmButton: false,
                    timer: 2500,

                })
                setTimeout(() => {
                    axios.post('/api/clientes', "primerNombre=" + this.primerNombre + "&apellido=" + this.apellido + "&email=" + this.email + "&password=" + this.password + "&numeroTel=" + this.numeroTelefono, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        .then(response => {

                            this.iniciarSesión()
                        })
                        .catch(error => console.log(`Error en: ${error}`))
                }, 2000);
            } else {
                Swal.fire({
                    title: 'Please fill in all the spaces',
                    // text: 'Please fill in all the spaces',
                    icon: "error",
                    showConfirmButton: false,
                    timer: 3000,
                })

            }


        }
}
})