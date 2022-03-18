var app = new Vue({
    el: "#app",
    data: {
        servicios: [],

        tipoServicio: "",
        valor: 0,
        fechaServicio: "",
        horaServicio: "",

        nombre: "",
        apellido: "",  

        rolCliente: false,
        rolAdmin: false,

		    buscador: "",
    },
    created() {
        this.loadData();
        this.cargarDatos();
    },

    computed: {
		filtrarObjetos() {
		  return this.servicios.filter(elemento => {
			if (elemento.tipoServicio) {
			  var nombre = elemento.tipoServicio.toLowerCase();
			  var buscado = this.buscador.toLowerCase();
			  if (nombre.includes(buscado)) {
				return elemento;
			  }
			}
		  });
		},
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
                return (window.location.href = "/web/html/home.html");
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