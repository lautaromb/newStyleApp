var app = new Vue({
  el: "#app",
  data: {
    productos: [],
  },
  created() {
      this.loadData();
  }
,
  methods: {
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