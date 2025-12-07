new Vue({
  el: '#app',
  router,
  data: {
    carrito: [],
    usuario: null
  },
  created() {
    this.cargarCarrito();
    this.verificarSesion();
  },
  methods: {
    cargarCarrito() {
      this.carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    },
    verificarSesion() {
      axios.get('/api/cliente/current')
        .then(response => this.usuario = response.data)
        .catch(() => this.usuario = null);
    }
  }
});