const ServiciosView = {
    data() {
        return {
            servicios: []
        };
    },
    computed: {
        carrito() {
            return store.state.carrito.filter(item => item.tipo === 'servicio');
        },
        total() {
            return this.carrito.reduce((sum, item) => {
                return sum + (item.valor * item.cantidad);
            }, 0).toFixed(2);
        }
    },
    created() {
        this.cargarServicios();
        
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
                        <h3>Servicios</h3>
                    </div>
                    <hr>

                    <div class="row align-items-start">
                        <service-card 
                            v-for="servicio in servicios" 
                            :key="servicio.id"
                            :servicio="servicio"
                            @agregar="agregarAlCarrito">
                        </service-card>
                    </div>
                </div>
            </section>

            <!-- MODAL CARRITO -->
            <div class="modal fade" id="modalCarritoServicios" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Mi Carrito - Servicios</h5>
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
                                        <div class="col-5 text-center">Servicio</div>
                                        <div class="col-3 text-center">Cantidad</div>
                                        <div class="col-3 text-center">Precio</div>
                                    </div>
                                    <hr class="d-none d-md-block">
                                    
                                    <div class="row align-items-center my-2" v-for="(item, index) in carrito" :key="index">
                                        <div class="col-1 text-center">
                                            <button class="btn btn-danger btn-sm" @click="quitarDelCarrito(index)">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </div>
                                        <div class="col-11 col-md-5">{{ item.nombre }}</div>
                                        <div class="col-6 col-md-3 text-center">{{ item.cantidad }}</div>
                                        <div class="col-6 col-md-3 text-center fw-bold">
                                            \${{ (item.valor * item.cantidad).toFixed(2) }}
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
                                    @click="reservar">
                                Reservar
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
        cargarServicios() {
            axios.get('/api/servicio')
                .then(response => {
                    this.servicios = response.data || [];
                })
                .catch(error => {
                    console.error('Error cargando servicios:', error);
                    this.servicios = [];
                });
        },
        
        agregarAlCarrito(servicio) {
            store.agregarAlCarrito(servicio, 'servicio');
            
            Swal.fire({
                icon: 'success',
                title: 'Servicio agregado',
                text: servicio.nombre + ' agregado al carrito',
                showConfirmButton: false,
                timer: 1000
            });
        },
        
        quitarDelCarrito(index) {
            store.quitarDelCarrito(index);
        },
        
        abrirModal() {
            const modal = new bootstrap.Modal(document.getElementById('modalCarritoServicios'));
            modal.show();
        },
        
        reservar() {
            if (!store.state.usuario) {
                Swal.fire({
                    icon: 'info',
                    title: 'Inicia sesión para reservar',
                    text: 'Tu carritose guardará automáticamente',
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
// Aquí deberías implementar el endpoint de compra de servicios
        Swal.fire({
            icon: 'info',
            title: 'Funcionalidad en desarrollo',
            text: 'La reserva de servicios se implementará próximamente',
            timer: 2000
        });
    }
}
};