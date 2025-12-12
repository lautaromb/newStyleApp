var app = new Vue({
    el: "#app",
    data: {
        nombre: "",
        valor: "",
        imagen: "",
        imagenCard:"",
        stockProducto: "",
        descripcion: "",
        feedback: "",
        
        rolAdmin: false,
    },

    created() {
        this.cargarDatos();
    },

    methods: {
        cargarDatos(){
            axios.get('/api/cliente/current')
            .then(response =>{
              if(response.data.email.includes("@admin.com")){
                this.rolAdmin = true;
              }
            })
        },

        crearProducto() {
            axios.post('/api/producto', `nombre=${this.nombre}&valor=${this.valor}&imagenProducto=${this.imagen}&imagenCard=${this.imagenCard}&stock=${this.stockProducto}&descripcion=${this.descripcion}`)
                .then((response) => {
                    Swal.fire({
                        title: `Producto creado`,
                        icon: "success",
                        showConfirmButton: false,
                        timer: 2000,
                    })

                })
        },
        crearServicio() {
            axios.post('/api/servicio', `nombre=${this.nombre}&valor=${this.valor}&imagenServicio=${this.imagen}&imagenCard=${this.imagenCard}&descripcion=${this.descripcion}`)
                .then((response) => {
                    Swal.fire({
                        title: `Servicio creado`,
                        icon: "success",
                        showConfirmButton: false,
                        timer: 2000,
                    })

                })
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