const app = new Vue({
    el: '#app',
    data: {
        ventas: [],
        filtro: null,
        cargando: false
    },
    created() {
        this.cargarVentas();
    },
    methods: {
        cargarVentas() {
            this.cargando = true;
            let url = '/api/despacho/ventas';
            if (this.filtro !== null) {
                url += `?entregado=${this.filtro}`;
            }

            axios.get(url)
                .then(response => {
                    if (Array.isArray(response.data)) {
                        this.ventas = response.data;
                    } else {
                        this.ventas = [];
                    }
                    this.cargando = false;
                })
                .catch(error => {
                    console.error('Error cargando ventas:', error);
                    this.cargando = false;
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'No se pudieron cargar las ventas'
                    });
                });
        },
        filtrarVentas(estado) {
            this.filtro = estado;
            this.cargarVentas();
        },
        marcarEntregado(id) {
            axios.patch(`/api/despacho/ventas/${id}/entregar`)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Venta marcada como entregada',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    this.cargarVentas();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.response?.data || 'No se pudo actualizar'
                    });
                });
        },
        desmarcarEntregado(id) {
            axios.patch(`/api/despacho/ventas/${id}/no-entregar`)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Venta desmarcada',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    this.cargarVentas();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.response?.data || 'No se pudo actualizar'
                    });
                });
        },
        formatearFecha(fecha) {
            if (!fecha) return '';
            const date = new Date(fecha);
            return date.toLocaleDateString('es-AR') + ' ' + date.toLocaleTimeString('es-AR');
        }
    }
});