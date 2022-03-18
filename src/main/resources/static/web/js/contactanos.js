var app = new Vue({
    el: "#app",
    data: {
        nombre: "",
        apellido: "",
        email: "",
        descripcion: "",
 
        nombre: "",
    	apellido: "",

        rolCliente: false,
        rolAdmin: false,
    },

    created() {
        this.cargarDatos();
    },

    methods: {
        cargarDatos(){
            axios.get('/api/cliente/current')
            .then(response =>{
                this.nombre = response.data.primerNombre;
        	    this.apellido = response.data.apellido;
              if(response.data.email.includes("@admin.com")){
                this.rolAdmin = true;
              }
              if(response.data.email.includes("@")){
                console.log(response.data.email)
                this.rolCliente = true;
              }
            })
        },

        enviarConsulta(){
            if(this.nombre != "" && this.nombre != "" && this.email.includes("@") && this.descripcion != ""){
                if(this.email.includes("@gmail.com") || this.email.includes("@hotmail.com") || this.email.includes("@mindhub.com")){
                    Swal.fire({
                        icon: 'success',
                        title: 'Correcto!',
                        text: 'Su consulta fue enviada con exito!! Pronto nos comunicaremos con usted..',
                        showConfirmButton: true,
                    });    
                }else{
                    Swal.fire({
                        icon: 'warning',
                        title: 'Ha ocurrido un error',
                        text: 'La asociacion de email no esta siendo admitida en estos momentos, sepa disculparnos',
                        showConfirmButton: true,
                    })
                }
            }else{
                Swal.fire({
                    icon: 'warning',
                    title: 'Ha ocurrido un error',
                    text: 'Verifique haber llenado correctamentes el formulario',
                    showConfirmButton: true,
                })
            }
            this.nombre = ""
            this.apellido = ""
            this.email = ""
            this.descripcion = ""
        },

        cerrarSesion() {
            axios
                .post("/api/logout")
                .then((response) => console.log("sesion cerrada!!!"))

            .then((response) => {
                console.log("signed in!!!");
                return (window.location.href = "/web/html/home.html");
            })

            .catch((e) => {
                console.log(e);
            });
        },
    },
});