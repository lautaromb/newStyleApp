var contcarrito = 0;
const app = new Vue({
    el: '#app',
    data: {
        total: null,
        servicios: [],
        carrito: [],
        contador: 1
    },
    created: function () {
        this.loadData();
        let datosDB = JSON.parse(localStorage.getItem('carrito-vue'));
        let datosDB2 = JSON.parse(localStorage.getItem('contador-vue'));
        if (datosDB === null) {
            this.carrito = [];
        } else {
            this.carrito = datosDB;
            contcarrito = datosDB2 || 0;
            this.calcular();
        }
    },
    methods: {
        loadData: function () {
            axios.get("/api/servicio")
                .then((response) => {
                    this.servicios = response.data || [];
                })
                .catch(() => {
                    this.servicios = [];
                });
        },

        agregarCarrito: function (index) {
            if (!this.carrito.length) {
                this.servicios[index].cantidad = 1;
                this.carrito.push(this.servicios[index]);
                contcarrito++;
                this.guardarLocal();
            } else {
                for (var i = 0; i < this.carrito.length; i++) {
                    if (this.carrito[i].nombre == this.servicios[index].nombre) {
                        this.carrito[i].cantidad++;
                        this.guardarLocal();
                        break;
                    } else if (i == (this.carrito.length - 1)) {
                        this.servicios[index].cantidad = 1;
                        this.carrito.push(this.servicios[index]);
                        contcarrito++;
                        this.guardarLocal();
                        break;
                    }
                }
            }
            this.$forceUpdate();
            this.calcular();
        },

        quitarProducto: function (index) {
            this.carrito.splice(index, 1);
            contcarrito = Math.max(0, contcarrito - 1);
            this.calcular();
            this.$forceUpdate();
            this.guardarLocal();
        },

        calcular: function () {
            var total = 0;
            for (var i = 0; i < this.carrito.length; i++) {
                total += (Number(this.carrito[i].cantidad) || 0) * (Number(this.carrito[i].valor) || 0);
            }
            this.total = total.toFixed(2);
            return this.total;
        },

        guardarLocal: function () {
            localStorage.setItem('carrito-vue', JSON.stringify(this.carrito));
            localStorage.setItem('contador-vue', JSON.stringify(contcarrito));
        },

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

            // Formato correcto para tu backend
            const carritoDTO = this.carrito.map(item => ({
                idProducto: item.id,
                nombre: item.nombre,
                valor: item.precio,
                cantidad: item.cantidad || 1
            }));

            axios.post('/api/compra', carritoDTO, {
                headers: { 'Content-Type': 'application/json' }
            })
                .then(resp => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Compra exitosa',
                        text: 'Tu compra se procesó correctamente.',
                        showConfirmButton: false,
                        timer: 1800
                    });

                    // Limpiar carrito
                    this.carrito = [];
                    this.calcular();
                    localStorage.removeItem('carrito-vue');
                    localStorage.removeItem('contador-vue');
                    contcarrito = 0;
                    $('#modal').modal('hide');

                    // Recargar productos para actualizar stock
                    this.loadData();
                })
                .catch(err => {
                    console.error('Error:', err);
                    const mensaje = err.response?.data || 'No se pudo procesar la compra';
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: mensaje,
                        showConfirmButton: false,
                        timer: 2500
                    });
                });
        },

        cerrarSesion() {
            axios.post("/api/logout")
                .then(() => {
                    window.location.href = "/web/html/index.html";
                })
                .catch((e) => {
                    console.log(e);
                });
        }
    }
});
