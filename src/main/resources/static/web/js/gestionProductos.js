const app = new Vue({
    el: '#app',
    data: {
        productos: [],
        productosFiltrados: [],
        busqueda: '',
        productoEditando: null,
        modalEditar: null
    },
    created() {
        this.cargarProductos();
    },
    mounted() {
        // Inicializar modal de Bootstrap
        this.modalEditar = new bootstrap.Modal(document.getElementById('modalEditar'));
    },
    methods: {
        cargarProductos() {
            axios.get('/api/producto')
                .then(response => {
                    this.productos = response.data;
                    this.productosFiltrados = response.data;
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
            if (!this.busqueda.trim()) {
                this.productosFiltrados = this.productos;
                return;
            }
            
            const busquedaLower = this.busqueda.toLowerCase();
            this.productosFiltrados = this.productos.filter(producto =>
                producto.nombre.toLowerCase().includes(busquedaLower)
            );
        },

        editarProducto(producto) {
            // Crear copia para editar
            this.productoEditando = { ...producto };
            this.modalEditar.show();
        },

        guardarEdicion() {
            if (!this.productoEditando) return;

            // Validaciones
            if (this.productoEditando.precio <= 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Precio inválido',
                    text: 'El precio debe ser mayor a 0',
                    timer: 2000
                });
                return;
            }

            if (this.productoEditando.stock < 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Stock inválido',
                    text: 'El stock no puede ser negativo',
                    timer: 2000
                });
                return;
            }

            // Crear DTO para enviar
            const productoDTO = {
                nombre: this.productoEditando.nombre,
                precio: parseFloat(this.productoEditando.precio),
                stock: parseInt(this.productoEditando.stock),
                descripcion: this.productoEditando.descripcion,
                imagenProducto: this.productoEditando.imagenProducto,
                imagenCard: this.productoEditando.imagenCard
            };

            axios.put(`/api/producto/${this.productoEditando.id}`, productoDTO)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Producto actualizado',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    this.modalEditar.hide();
                    this.cargarProductos();
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.response?.data || 'No se pudo actualizar el producto',
                        timer: 2500
                    });
                });
        },

        eliminarProducto(id) {
            Swal.fire({
                title: '¿Estás seguro?',
                text: 'Esta acción desactivará el producto',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.delete(`/api/producto/${id}`)
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                title: 'Producto eliminado',
                                showConfirmButton: false,
                                timer: 1500
                            });
                            this.cargarProductos();
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: error.response?.data || 'No se pudo eliminar el producto',
                                timer: 2500
                            });
                        });
                }
            });
        }
    }
});