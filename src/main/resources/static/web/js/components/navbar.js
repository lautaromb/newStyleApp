Vue.component('navbar-component', {
    props: ['usuario', 'carritoCount'],
    template: `
        <header>
            <nav>
                <div class="logo">New Style</div>
                <input type="checkbox" id="click">
                <label for="click" class="menu-btn">
                    <i class="fas fa-bars"></i>
                </label>
                <ul>
                    <li>
                        <router-link to="/" exact>Inicio</router-link>
                    </li>
                    <li>
                        <router-link to="/servicios">Servicios</router-link>
                    </li>
                    <li>
                        <router-link to="/productos">Productos</router-link>
                    </li>
                    
                    <!-- Links para usuarios autenticados -->
                    <template v-if="usuario">
                        <!-- Links solo para admin -->
                        <template v-if="esAdmin">
                            <li>
                                <router-link to="/formulario-servicio">Crear Servicio</router-link>
                            </li>
                            <li>
                                <router-link to="/formulario-producto">Crear Producto</router-link>
                            </li>
                            <li>
                                <router-link to="/informes">Informes</router-link>
                            </li>
                            <li>
                                <router-link to="/despacho">Despacho</router-link>
                            </li>
                        </template>
                        
                        <!-- Links para todos los autenticados -->
                        <li>
                            <router-link to="/saldo">Mi Saldo</router-link>
                        </li>
                        <li>
                            <a href="#" @click.prevent="$emit('logout')">Salir</a>
                        </li>
                    </template>
                    
                    <!-- Link para no autenticados -->
                    <template v-else>
                        <li>
                            <router-link to="/login">Iniciar Sesión</router-link>
                        </li>
                    </template>
                    
                    <!-- Icono de carrito -->
                    <li class="carrito-container">
                        <box-icon name='cart' color='#ffffff' @click="abrirCarrito"></box-icon>
                        <span v-if="carritoCount > 0" class="carrito-badge">{{ carritoCount }}</span>
                    </li>
                </ul>
            </nav>
        </header>
    `,
    computed: {
        esAdmin() {
            return this.usuario && this.usuario.email.includes('@admin.com');
        }
    },
    methods: {
        abrirCarrito() {
            this.$root.$emit('abrir-modal-carrito');
        }
    }
});