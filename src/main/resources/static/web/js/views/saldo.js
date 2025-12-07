// Vista mínima para /saldo (expone globalmente SaldoView para el router)
window.SaldoView = {
  template: `
    <section class="container py-4">
      <h2>Mi Saldo</h2>
      <div v-if="loading">Cargando...</div>
      <div v-else-if="error" class="text-danger">
        {{ error }} <a href="/web/index.html">Iniciar sesión</a>
      </div>
      <div v-else>
        <p>Usuario: <strong>{{ user?.email || 'N/D' }}</strong></p>
        <p>Saldo disponible: <strong>{{ formatCurrency(saldo) }}</strong></p>
        <button class="btn btn-primary" @click="refresh">Actualizar</button>
      </div>
    </section>
  `,
  data() {
    return { loading: true, error: null, saldo: null, user: null };
  },
  created() {
    this.loadSaldo();
  },
  methods: {
    loadSaldo() {
      this.loading = true;
      this.error = null;
      if (typeof axios === 'undefined') {
        this.error = 'Axios no disponible';
        this.loading = false;
        return;
      }
      axios.get('/api/cliente/current', { withCredentials: true })
        .then(res => {
          this.user = res.data || null;
          // ajusta según la propiedad real de saldo en tu backend
          this.saldo = (res.data && (res.data.saldo || res.data.balance || res.data.monto)) ?? 0;
        })
        .catch(err => {
          this.error = 'No autenticado o error al obtener saldo';
          console.error(err);
        })
        .finally(() => { this.loading = false; });
    },
    refresh() { this.loadSaldo(); },
    formatCurrency(v) {
      const n = Number(v || 0);
      return n.toLocaleString('es-AR', { style: 'currency', currency: 'ARS' });
    }
  }
};