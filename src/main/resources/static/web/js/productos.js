var app = new Vue({
  el: "#app",
  data: {
    productos: [],
    rolAdmin: false,
    buscador: "",
  },
  created() {
      this.cargarDatos();
      this.loadData();
  },
  computed: {
    filtrarObjetos() {
      return this.productos.filter(elemento => {
        if (elemento.nombre) {
          var nombre = elemento.nombre.toLowerCase();
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
        if(response.data.email.includes("@admin.com")){
          this.rolAdmin = true;
        }
      })
    },

      loadData: function () {
          axios.get("/api/producto").then((response) => {
            console.log(response.data);
            this.productos = response.data;
            
            console.log(this.productos);
    
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
  },
});