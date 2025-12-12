var contcarrito = 0;
const app = new Vue({
    el: '#app',
    data: {
        total: null,
        productos: [],
        carrito: [],
        contador: 1
    },
    created: function () {
        this.loadData();
        // Cargar carrito desde localStorage
        let datosDB = JSON.parse(localStorage.getItem('carrito-productos-vue'));
        let datosDB2 = JSON.parse(localStorage.getItem('contador-productos-vue'));
        if (datosDB === null) {
            this.carrito = [];
        } else {
            this.carrito = datosDB;
            contcarrito = datosDB2 || 0;
            updateCartCount(this.carrito.length);
            this.calcular();
        }
    },
    methods: {
        // Cargar productos desde el backend
        loadData: function () {
            axios.get("/api/producto")
                .then((response) => {
                    this.productos = response.data || [];
                })
                .catch((error) => {
                    console.error("Error cargando productos:", error);
                    this.productos = [];
                });
        },

        // Agregar producto al carrito
        agregarCarrito: function (index) {
            const producto = this.productos[index];

            // Verificar si ya existe en el carrito
            const indexEnCarrito = this.carrito.findIndex(item => item.id === producto.id);

            if (indexEnCarrito !== -1) {
                // Ya existe, incrementar cantidad
                this.carrito[indexEnCarrito].cantidad++;
            } else {
                // No existe, agregarlo con cantidad 1
                const nuevoItem = {
                    id: producto.id,
                    nombre: producto.nombre,
                    precio: producto.precio,
                    cantidad: 1,
                    stock: producto.stock
                };
                this.carrito.push(nuevoItem);
                contcarrito++;
                updateCartCount(this.carrito.length);
            }

            this.guardarLocal();
            this.calcular();
            this.$forceUpdate();

            // 🎉 Feedback visual
            Swal.fire({
                icon: 'success',
                title: 'Producto agregado',
                text: producto.nombre,
                showConfirmButton: false,
                timer: 1500,
                toast: true,
                position: 'top-end'
            });
        },

        // Quitar producto del carrito
        quitarProducto: function (index) {
            this.carrito.splice(index, 1);
            contcarrito = Math.max(0, contcarrito - 1);
            this.calcular();
            updateCartCount(this.carrito.length);
            this.$forceUpdate();
            this.guardarLocal();
        },

        // Calcular total del carrito
        calcular: function () {
            var total = 0;
            for (var i = 0; i < this.carrito.length; i++) {
                const cantidad = Number(this.carrito[i].cantidad) || 0;
                const precio = Number(this.carrito[i].precio) || 0;
                total += cantidad * precio;
            }
            this.total = total.toFixed(2);
            return this.total;
        },
        actualizarCantidad(servicio) {
            if (servicio.cantidad < 1) servicio.cantidad = 1;
            if (servicio.cantidad > servicio.stock) servicio.cantidad = servicio.stock;

            this.calcular();
            updateCartCount(this.carrito.length);
            localStorage.setItem('carrito-productos-vue', JSON.stringify(this.carrito));
        },

        // Guardar carrito en localStorage
        guardarLocal: function () {
            localStorage.setItem('carrito-productos-vue', JSON.stringify(this.carrito));
            localStorage.setItem('contador-productos-vue', JSON.stringify(contcarrito));
        },

        // 🔐 GUEST CHECKOUT: Verificar autenticación antes de comprar
        comprar() {
            if (!this.carrito || !this.carrito.length) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Carrito vacío',
                    text: 'Agrega productos antes de comprar.',
                    showConfirmButton: false,
                    timer: 1800
                });
                return;
            }

            // 🔍 Verificar si está autenticado
            axios.get('/api/cliente/current')
                .then(response => {
                    // ✅ Usuario autenticado, proceder con la compra
                    this.procesarCompra();
                })
                .catch(error => {
                    // ❌ No autenticado, redirigir a login
                    Swal.fire({
                        icon: 'info',
                        title: 'Inicia sesión para continuar',
                        text: 'Debes iniciar sesión para completar tu compra. Tu carrito se guardará.',
                        showConfirmButton: true,
                        confirmButtonText: 'Ir al login',
                        confirmButtonColor: '#007bff',
                        showCancelButton: true,
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Guardar carrito y redirigir
                            this.guardarLocal();
                            window.location.href = 'index.html';
                        }
                    });
                });
        },

        // 💳 Procesar la compra (solo si está autenticado)
        procesarCompra() {
            // Validar stock antes de enviar
            for (let item of this.carrito) {
                const producto = this.productos.find(p => p.id === item.id);
                if (producto && producto.stock < item.cantidad) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Stock insuficiente',
                        text: `No hay suficiente stock de "${item.nombre}". Disponible: ${producto.stock}`,
                        timer: 3000
                    });
                    return;
                }
            }

            // Formato esperado por el backend
            const carritoDTO = this.carrito.map(item => ({
                idProducto: item.id,
                nombre: item.nombre,
                valor: item.precio,
                cantidad: item.cantidad
            }));

            axios.post('/api/compra', carritoDTO, {
                headers: { 'Content-Type': 'application/json' }
            })
                .then(resp => {
                    Swal.fire({
                        icon: 'success',
                        title: '¡Compra exitosa!',
                        text: 'Tu compra se procesó correctamente.',
                        showConfirmButton: false,
                        timer: 2000
                    });

                    // Limpiar carrito
                    this.carrito = [];
                    this.calcular();
                    localStorage.removeItem('carrito-productos-vue');
                    localStorage.removeItem('contador-productos-vue');
                    contcarrito = 0;
                    updateCartCount(0);

                    // $('#modal').modal('hide');

                    const modalEl = document.getElementById('modal');
                    const modalInstance =
                        bootstrap.Modal.getInstance(modalEl) ||
                        new bootstrap.Modal(modalEl);

                    modalInstance.hide();

                    // Recargar productos para actualizar stock
                    this.loadData();
                })
                .catch(err => {
                    console.error('Error en la compra:', err);
                    const mensaje = err.response?.data || 'No se pudo procesar la compra';
                    Swal.fire({
                        icon: 'error',
                        title: 'Error en la compra',
                        text: mensaje,
                        showConfirmButton: true,
                        timer: 3000
                    });
                });
        }

    },

});

window.vueApp = app;