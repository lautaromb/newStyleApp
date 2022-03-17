var app = new Vue({
  el: "#app",
  data: {
    email: "",
    password: "",

    rolAdmin: false,
    servicios:[],
    productos:[]
  },

  created(){
    this.cargarDatos()
  },

  methods: {

    cargarDatos(){
      axios.get('/api/cliente/current')
      .then(response =>{
        if(response.data.email.includes("@admin.com")){
          this.rolAdmin = true;
          console.log(response.data.email)
        }
      })
    },

    signIn() {
      axios
        .post(
          "/api/login",
          "email=" + this.email + "&password=" + this.password,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => {
          
          window.location.href = "/web/home.html";
        })
        .catch((error) => {
          Swal.fire({
            title: "Invalid combination",
            text: "Please re-enter your details",
            icon: "error",
            showConfirmButton: false,
            timer: 2000,
          });
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



    /*Nuevo*/
    
  //   loadFunciones: function() {
  //     axios.get("/api/servicio").then((response) => {
  //         console.log(response.data);
  //         this.servicios = response.data;
  //     });
  // },
  //   loadData: function() {
  //   axios.get("/api/servicio").then((response) => {
  //       console.log(response.data);
  //       this.servicios = response.data;
  //   });
  // }



  },
});
