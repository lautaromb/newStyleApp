var app = new Vue({
    el: "#app",
    data: {
     
    },
  
    methods: {
      
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