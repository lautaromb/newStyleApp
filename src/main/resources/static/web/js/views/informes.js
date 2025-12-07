// Vista mínima placeholder para Informes
window.InformesView = {
  template: `
    <section class="container py-4">
      <h2>Informes</h2>
      <div v-if="loading">Cargando...</div>
      <div v-else-if="error" class="text-danger">{{ error }}</div>
      <div v-else>
        <p>No hay datos disponibles (placeholder).</p>
      </div>
    </section>
  `,
  data() { return { loading: false, error: null, data: null }; },
  created() {
    // cargar datos cuando lo desarrolles
  }
};