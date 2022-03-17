var app = new Vue({
    el: "#app",
    data: {
        servicios: [],

        tipoServicio: "",
        valor: 0,
        fechaServicio: "",
        horaServicio: ""

    },
    created() {
        this.loadData();
    },
    methods: {
        loadData: function() {
            axios.get("/api/servicio").then((response) => {
                console.log(response.data);
                this.servicios = response.data;

                this.filtroServicio = this.servicios.filter(servicio => servicio.tipoServicio == this.tipoServicio)
                this.valor = this.filtroServicio[0].valor
            });
        },
        cerrarSesion() {
            axios
                .post("/api/logout")
                .then((response) => console.log("sesion cerrada!!!"))

            .then((response) => {
                console.log("signed in!!!");
                return (window.location.href = "/web/html/index.html");
            })

            .catch((e) => {
                console.log(e);
            });
        },

        crearTurnoServicio() {
            axios.post("/api/generar/turnos", { "tipoServicio": this.tipoServicio, "valor": this.valor, "fechaServicio": this.fechaServicio, "horaServicio": this.horaServicio })
                .then((response) => {
                    Swal.fire({
                            title: `Se generó turno correctamente`,
                            icon: "success",
                            showConfirmButton: false,
                        }),

                        setTimeout(() => {
                            window.location.href = "/web/servicios.html"

                        }, 3000)
                })
                .catch(error => {
                    console.log(`Error en: ${error}`)
                })
        }


    },
});