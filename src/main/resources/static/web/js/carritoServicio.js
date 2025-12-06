var contcarrito = 0;
const app = new Vue({
    el: '#app',
    data: {
        total: null,
        servicios: [],
        carrito: [],
        contador: 1
    },
    created: function(){
        this.loadData();
        let datosDB = JSON.parse(localStorage.getItem('carrito-vue'));
        let datosDB2 = JSON.parse(localStorage.getItem('contador-vue'));
        if(datosDB === null){
            this.carrito = [];
        }else{
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

        agregarCarrito: function(index){
            if(!this.carrito.length){
                this.servicios[index].cantidad = 1;
                this.carrito.push(this.servicios[index]);
                contcarrito++;
                this.guardarLocal();
            }else{
                for (var i = 0; i < this.carrito.length; i++) {
                    if(this.carrito[i].nombre == this.servicios[index].nombre){
                        this.carrito[i].cantidad++;
                        this.guardarLocal();
                        break;
                    }else if(i == (this.carrito.length-1)){
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

        quitarProducto: function (index){
            this.carrito.splice(index,1);
            contcarrito = Math.max(0, contcarrito - 1);
            this.calcular();
            this.$forceUpdate();
            this.guardarLocal();
        },

        calcular: function(){
            var total = 0;
            for (var i = 0; i < this.carrito.length; i++) {
                total += (Number(this.carrito[i].cantidad) || 0) * (Number(this.carrito[i].valor) || 0);
            }
            this.total = total.toFixed(2);
            return this.total;
        },

        guardarLocal: function(){
            localStorage.setItem('carrito-vue', JSON.stringify(this.carrito));
            localStorage.setItem('contador-vue', JSON.stringify(contcarrito));
        },

        comprar() {
            if (!this.carrito || !this.carrito.length) {
                Swal.fire({ icon: 'warning', title: 'Carrito vacío', text: 'Agrega servicios antes de comprar.', showConfirmButton: false, timer: 1800 });
                return;
            }

            const orden = {
                items: this.carrito.map(it => ({ id: it.id, nombre: it.nombre, precio: it.valor || it.precio, cantidad: it.cantidad || 1 })),
                total: Number(this.total) || 0
            };

            axios.post('/api/ordenes', orden)
                .then(resp => {
                    Swal.fire({ icon: 'success', title: 'Compra enviada', text: 'Gracias por tu compra.', showConfirmButton: false, timer: 1800 });
                    this.carrito = [];
                    this.calcular();
                    localStorage.removeItem('carrito-vue');
                    localStorage.removeItem('contador-vue');
                    contcarrito = 0;
                    $('#modal').modal('hide');
                    if (resp.data && resp.data.paymentUrl) window.location.href = resp.data.paymentUrl;
                })
                .catch(err => {
                    console.warn('Error al procesar compra:', err);
                    Swal.fire({ icon: 'error', title: 'Error', text: 'No se pudo procesar la compra. Intenta nuevamente.', showConfirmButton: false, timer: 2000 });
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
