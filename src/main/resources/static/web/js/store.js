// store.js - Estado global simple (sin Vuex por ahora)
const store = {
    state: {
        usuario: null,
        carrito: [],
        loading: false
    },
    
    // Cargar carrito del localStorage
    cargarCarrito() {
        const carritoProductos = JSON.parse(localStorage.getItem('carrito-productos-vue')) || [];
        const carritoServicios = JSON.parse(localStorage.getItem('carrito-servicios-vue')) || [];
        this.state.carrito = [...carritoProductos, ...carritoServicios];
    },
    
    // Guardar carrito en localStorage
    guardarCarrito() {
        const productos = this.state.carrito.filter(item => item.tipo === 'producto');
        const servicios = this.state.carrito.filter(item => item.tipo === 'servicio');
        
        localStorage.setItem('carrito-productos-vue', JSON.stringify(productos));
        localStorage.setItem('carrito-servicios-vue', JSON.stringify(servicios));
    },
    
    // Agregar item al carrito
    agregarAlCarrito(item, tipo) {
        const index = this.state.carrito.findIndex(i => i.id === item.id && i.tipo === tipo);
        
        if (index !== -1) {
            this.state.carrito[index].cantidad++;
        } else {
            this.state.carrito.push({
                ...item,
                tipo: tipo,
                cantidad: 1
            });
        }
        
        this.guardarCarrito();
    },
    
    // Quitar item del carrito
    quitarDelCarrito(index) {
        this.state.carrito.splice(index, 1);
        this.guardarCarrito();
    },
    
    // Limpiar carrito
    limpiarCarrito() {
        this.state.carrito = [];
        localStorage.removeItem('carrito-productos-vue');
        localStorage.removeItem('carrito-servicios-vue');
    },
    
    // Calcular total
    calcularTotal() {
        return this.state.carrito.reduce((total, item) => {
            const precio = item.precio || item.valor || 0;
            return total + (precio * item.cantidad);
        }, 0);
    }
};