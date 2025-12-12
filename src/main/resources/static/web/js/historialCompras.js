const app = new Vue({
    el: '#app',
    data: {
        compras: []
    },
    created() {
        this.cargarHistorial();
    },
    methods: {
        cargarHistorial() {
            axios.get('/api/compras/historial')
                .then(response => {
                    this.compras = response.data;
                    console.log('Compras cargadas:', this.compras);
                    // Ordenar por ID descendente (más recientes primero)
                    this.compras.sort((a, b) => b.id - a.id);
                })
                .catch(error => {
                    console.error('Error:', error);
                    if (error.response?.status === 401) {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Sesión expirada',
                            text: 'Por favor inicia sesión nuevamente',
                            timer: 2000
                        }).then(() => {
                            window.location.href = 'index.html';
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'No se pudo cargar el historial',
                            timer: 2000
                        });
                    }
                });
        },

        calcularTotalGastado() {
            const total = this.compras.reduce((sum, compra) => 
                sum + (Number(compra.total) || 0), 0
            );
            return total.toFixed(2);
        },

        calcularTotalProductos() {
            return this.compras.reduce((sum, compra) => {
                const productosCompra = compra.productos.reduce((psum, prod) => 
                    psum + (Number(prod.cantidad) || 0), 0
                );
                return sum + productosCompra;
            }, 0);
        }
    }
});