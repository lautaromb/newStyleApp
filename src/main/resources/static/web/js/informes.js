const app = new Vue({
    el: '#app',
    data: {
        informes: []
    },
    computed: {
        totalCantidad() {
            return this.informes.reduce((sum, item) => sum + item.cantidadVendida, 0);
        },
        totalRecaudado() {
            return this.informes.reduce((sum, item) => sum + item.totalRecaudado, 0);
        }
    },
    created() {
        this.cargarInformes();
    },
    methods: {
        cargarInformes() {
            axios.get('/api/informes/productos')
                .then(response => {
                    this.informes = response.data;
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'No se pudieron cargar los informes',
                        timer: 2000
                    });
                });
        }
    }
});