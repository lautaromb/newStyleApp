var app = new Vue({
    el: "#app",
    data: {
        nombre: "",
        valor: "",
        imagen: "",
        imagenCard: "",
        stockProducto: "",
        descripcion: "",
        feedback: "",

        rolAdmin: false,
    },

    created() {
        this.cargarDatos();
    },

    methods: {
        cargarDatos() {
            axios.get('/api/cliente/current')
                .then(response => {
                    if (response.data.email.includes("@admin.com")) {
                        this.rolAdmin = true;
                    }
                })
        },

crearProducto() {
    const datos = {
        nombre: this.nombre,
        precio: parseFloat(this.valor),
        imagenProducto: this.imagen,
        imagenCard: this.imagenCard,
        stock: parseInt(this.stockProducto),
        descripcion: this.descripcion
    };
    
    axios.post('/api/producto', datos)
        .then((response) => {
            console.log('Respuesta exitosa:', response.status, response.data);
            Swal.fire({
                title: `Producto creado`,
                icon: "success",
                showConfirmButton: false,
                timer: 2000,
            })
            // Limpiar formulario
            this.nombre = "";
            this.valor = "";
            this.imagen = "";
            this.imagenCard = "";
            this.stockProducto = "";
            this.descripcion = "";
        })
        .catch((error) => {
            console.error('Error status:', error.response?.status);
            console.error('Error data:', error.response?.data);
            console.error('Error message:', error.message);
        });
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