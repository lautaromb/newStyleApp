const ProductosView = {
    data() {
        return {
            productos: [],
            mostrarModal: false
        };
    },
    computed: {
        carrito() {
            return store.state.carrito.filter(item => item.tipo === 'producto');
        },
        total() {
            return this.carrito.reduce((sum, item) => {
                return sum + (item.precio * item.cantidad);
            }, 0).toFixed(2);
        }
    },
    created() {
        this.cargarProductos();
        
        // Escuchar evento para abrir modal
        this.$root.$on('abrir-modal-carrito', () => {
            this.abrirModal();
        });
    },
    template: `
        <main>
            <section style="margin-top: 1.5rem;">
                <div class="container-fluid">
                    <hr>
                    <div class="p-2" style="text-align: center;">
                        <h3>Productos</h3>
                    </div>
                    <hr>

                    <div class="row align-items-start">
                        <product-card 
                            v-for="producto in productos" 
                            :key="producto.id"
                            :producto="producto"
                            @agregar="agregarAlCarrito">
                        </product-card>
                    </div>
                </div>
            </section>

            <!-- MODAL CARRITO -->
            <div class="modal fade" id="modalCarrito" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Mi Carrito</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <div class="container">
                                <div v-if="carrito.length === 0" class="text-center py-4">
                                    <p>Tu carrito está vacío</p>
                                </div>
                                
                                <div v-else>
                                    <div class="row d-none d-md-flex fw-bold">
                                        <div class="col-1 text-center"></div>
                                        <div class="col-4 text-center">Producto</div>
                                        <div class="col-3 text-center">Cantidad</div>
                                        <div class="col-2 text-center">Precio</div>
                                        <div class="col-2 text-center">Subtotal</div>
                                    </div>
                                    <hr class="d-none d-md-block">
                                    
                                    <div class="row align-items-center my-2" v-for="(item, index) in carrito" :key="index">
                                        <div class="col-1 text-center">
                                            <button class="btn btn-danger btn-sm" @click="quitarDelCarrito(index)">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </div>
                                        <div class="col-11 col-md-4">{{ item.nombre }}</div>
                                        <div class="col-6 col-md-3 text-center">{{ item.cantidad }}</div>
                                        <div class="col-6 col-md-2 text-center">\${{ item.precio }}</div>
                                        <div class="col-12 col-md-2 text-center fw-bold">
                                            \${{ (item.precio * item.cantidad).toFixed(2) }}
                                        </div>
                                        <div class="col-12"><hr class="d-md-none"></div>
                                    </div>
                                    
                                    <hr>
                                    <div class="row">
                                        <div class="col-6 text-end"><h4>Total:</h4></div>
                                        <div class="col-6"><h4 class="text-success">\${{ total }}</h4></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="button" class="btn btn-primary" 
                                    :disabled="carrito.length === 0" 
                                    @click="comprar">
                                Comprar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="p-2 link-inicio">
                <div class="div-link-inicio">
                    <a href="#app">Volver Arriba</a>
                </div>
            </div>
        </main>
    `,
    methods: {
        cargarProductos() {
            axios.get('/api/producto')
                .then(response => {
                    this.productos = response.data || [];
                })
                .catch(error => {
                    console.error('Error cargando productos:', error);
                    this.productos = [];
                });
        },
        
        agregarAlCarrito(producto) {
            store.agregarAlCarrito(producto, 'producto');
            
            Swal.fire({
                icon: 'success',
                title: 'Producto agregado',
                text: producto.nombre + ' agregado al carrito',
                showConfirmButton: false,
                timer: 1000
            });
        },
        
        quitarDelCarrito(index) {
            store.quitarDelCarrito(index);
        },
        
        abrirModal() {
            const modal = new bootstrap.Modal(document.getElementById('modalCarrito'));
            modal.show();
        },
        
        comprar() {
            if (!store.state.usuario) {
                Swal.fire({
                    icon: 'info',
                    title: 'Inicia sesión para comprar',
                    text: 'Tu carrito se guardará automáticamente',
                    showConfirmButton: true,
                    confirmButtonText: 'Ir a Login',
                    showCancelButton: true,
                    cancelButtonText: 'Cancelar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        this.$router.push('/login');
                    }
                });
                return;
            }

            const carritoDTO = this.carrito.map(item => ({
                idProducto: item.id,
                nombre: item.nombre,
                valor: item.precio,
                cantidad: item.cantidad
            }));

            axios.post('/api/compra', carritoDTO, {
                headers: { 'Content-Type': 'application/json' }
            })
            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: 'Compra exitosa',
                    text: 'Tu compra se procesó correctamente.',
                    showConfirmButton: false,
                    timer: 1800
                });
                
                // Limpiar solo productos del carrito
                store.state.carrito = store.state.carrito.filter(item => item.tipo !== 'producto');
                store.guardarCarrito();
                
                // Cerrar modal
                bootstrap.Modal.getInstance(document.getElementById('modalCarrito')).hide();
                
                // Recargar productos
                this.cargarProductos();
            })
            .catch(error => {
                console.error('Error:', error);
                const mensaje = error.response?.data || 'No se pudo procesar la compra';
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: mensaje,
                    showConfirmButton: false,
                    timer: 2500
                });
            });
        }
    }
};