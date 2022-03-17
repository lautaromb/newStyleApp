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
                        title: 'Error',
                        text: 'Email o contraseña incorrecta. Vuelve a intentarlo.',
                        icon: "error",
                        showConfirmButton: false,
                        timer: 2200,
                    })
                })
        },
        registrarse() {
            if(this.primerNombre != '' && this.apellido != '' && this.email.includes("@") != '' && this.password != '' && this.numeroTelefono){
              Swal.fire({
                icon: 'success',
                title: 'Te registraste correctamente!',
                showConfirmButton: false,
                timer: 2000
              })
              setTimeout(() => {
                axios
                .post(
                  "/api/clientes",
                  `primerNombre=${this.primerNombre}&apellido=${this.apellido}&email=${this.email}&password=${this.password}&numeroTel=${this.numeroTelefono}`
                )
                .then((response) => {
                    console.log("registered");
                    
                  this.iniciarSesion()
      
                  })
        
                .catch((e) => {
                  console.log(e);
                });
              }, 2100);
            } else {
              Swal.fire({
                text: 'Por favor completa los datos anteriores correctamente',
                icon: 'error',
                showConfirmButton: false,
                timer: 2500
              })
            }  
            
          }
}
})