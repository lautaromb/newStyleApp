const app = new Vue({
    el: '#app',
    data: {
        productos: [],
        productosFiltrados: [],
        busqueda: '',
        filtroStock: 'todos',
        productoEditando: null,
        nuevoStock: 0,
        motivoAjuste: '',
        modalStock: null
    },
    computed: {
        productosStockBajo() {
            return this.productos.filter(p => p.stock < 10);
        }
    },
    created() {
        this.cargarProductos();
    },
    mounted() {
        this.modalStock = new bootstrap.Modal(document.getElementById('modalStock'));
    },
    methods: {
        cargarProductos() {
            axios.get('/api/producto')
                .then(response => {
                    this.productos = response.data.filter(p => p.activo);
                    this.filtrarProductos();
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'No se pudieron cargar los productos',
                        timer: 2000
                    });
                });
        },

        filtrarProductos() {
            let resultado = this.productos;

            // Filtro por búsqueda
            if (this.busqueda.trim()) {
                const busquedaLower = this.busqueda.toLowerCase();
                resultado = resultado.filter(p =>
                    p.nombre.toLowerCase().includes(busquedaLower)
                );
            }

            // Filtro por nivel de stock
            switch (this.filtroStock) {
                case 'bajo':
                    resultado = resultado.filter(p => p.stock < 10);
                    break;
                case 'medio':
                    resultado = resultado.filter(p => p.stock >= 10 && p.stock < 50);
                    break;
                case 'alto':
                    resultado = resultado.filter(p => p.stock >= 50);
                    break;
            }

            this.productosFiltrados = resultado;
        },

        modificarStock(producto) {
            this.productoEditando = { ...producto };
            this.nuevoStock = producto.stock;
            this.motivoAjuste = '';
            this.modalStock.show();
        },

        calcularDiferencia() {
            if (!this.productoEditando) return '';
            const diferencia = this.nuevoStock - this.productoEditando.stock;
            if (diferencia > 0) {
                return `+${diferencia} unidades`;
            } else if (diferencia < 0) {
                return `${diferencia} unidades`;
            } else {
                return 'Sin cambios';
            }
        },

        guardarStock() {
            if (this.nuevoStock < 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Stock inválido',
                    text: 'El stock no puede ser negativo',
                    timer: 2000
                });
                return;
            }

            if (this.nuevoStock === this.productoEditando.stock) {
                Swal.fire({
                    icon: 'info',
                    title: 'Sin cambios',
                    text: 'El stock no ha cambiado',
                    timer: 2000
                });
                return;
            }

            // Actualizar el producto completo con el nuevo stock
            const productoDTO = {
                nombre: this.productoEditando.nombre,
                precio: this.productoEditando.precio,
                stock: parseInt(this.nuevoStock),
                descripcion: this.productoEditando.descripcion,
                imagenProducto: this.productoEditando.imagenProducto,
                imagenCard: this.productoEditando.imagenCard
            };

            axios.put(`/api/producto/${this.productoEditando.id}`, productoDTO)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Stock actualizado',
                        text: this.motivoAjuste ? `Motivo: ${this.motivoAjuste}` : '',
                        showConfirmButton: false,
                        timer: 2000
                    });
                    this.modalStock.hide();
                    this.cargarProductos();
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.response?.data || 'No se pudo actualizar el stock',
                        timer: 2500
                    });
                });
        }
    }
});