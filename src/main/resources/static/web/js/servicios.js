var app = new Vue({
    el: "#app",
    data: {
      servicios: [],
    },
    created() {
        this.loadData();
    }
  ,
    methods: {
        loadData: function () {
            axios.get("/api/servicio").then((response) => {
              console.log(response.data);
              this.servicios = response.data;
              
              console.log(this.servicios);
      
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