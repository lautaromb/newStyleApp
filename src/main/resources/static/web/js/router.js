// router.js - Configuración de rutas
const routes = [
  { path: '/', component: window.HomeView || { template: '<div>Home</div>' } },
  { path: '/login', component: window.LoginView || { template: '<div>Login</div>' }, meta: { guestOnly: true } },
  { path: '/productos', component: window.ProductosView || { template: '<div>Productos</div>' } },
  { path: '/servicios', component: window.ServiciosView || { template: '<div>Servicios</div>' } },
  { path: '/saldo', component: window.SaldoView || { template: '<div>Saldo</div>' }, meta: { requiresAuth: true } },
  { path: '/informes', component: window.InformesView || { template: '<div>Informes</div>' }, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/despacho', component: window.DespachoView || { template: '<div>Despacho</div>' }, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/formulario-producto', component: window.FormularioProductoView || { template: '<div>Formulario Producto</div>' }, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/formulario-servicio', component: window.FormularioServicioView || { template: '<div>Formulario Servicio</div>' }, meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '*', redirect: '/' } // Redirigir rutas no encontradas al home
];

const router = new VueRouter({
  mode: 'history',
  routes
});

// Exponer globalmente para que app.js lo encuentre de forma fiable
window.router = router;

// Guard de navegación global
router.beforeEach((to, from, next) => {
    const usuario = store.state.usuario;
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    const requiresAdmin = to.matched.some(record => record.meta.requiresAdmin);
    const guestOnly = to.matched.some(record => record.meta.guestOnly);

    // Si la ruta requiere autenticación
    if (requiresAuth) {
        if (!usuario) {
            // No está autenticado, redirigir a login
            Swal.fire({
                icon: 'warning',
                title: 'Acceso denegado',
                text: 'Debes iniciar sesión',
                showConfirmButton: false,
                timer: 1500
            });
            next('/login');
        } else if (requiresAdmin && !usuario.email.includes('@admin.com')) {
            // No es admin, redirigir a home
            Swal.fire({
                icon: 'error',
                title: 'Acceso denegado',
                text: 'No tienes permisos de administrador',
                showConfirmButton: false,
                timer: 1500
            });
            next('/');
        } else {
            // Todo OK
            next();
        }
    } 
    // Si la ruta es solo para invitados (login/registro)
    else if (guestOnly && usuario) {
        // Ya está autenticado, redirigir a home
        next('/');
    } 
    // Ruta pública
    else {
        next();
    }
});