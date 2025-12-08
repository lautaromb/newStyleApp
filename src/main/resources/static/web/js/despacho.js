import axios from '../js/axios-config';
const app = new Vue({
    el: '#app',
    data: {
        ventas: [],
        filtro: null
    },
    created() {
        this.cargarVentas();
    },
    methods: {
        cargarVentas() {
            let url = '/api/despacho/ventas';
            if (this.filtro !== null) {
                url += `?entregado=${this.filtro}`;
            }

            axios.get(url)
                .then(response => {
                    this.ventas = response.data;
                })
                .catch(error => {
                    console.error('Error:', error);
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
                        text: error.response?.data || 'No se pudo actualizar',
                        timer: 2000
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
                        text: error.response?.data || 'No se pudo actualizar',
                        timer: 2000
                    });
                });
        }
    }
});