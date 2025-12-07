// router.js - Configuración de rutas
const router = new VueRouter({
    mode: 'hash', // Usa # en las URLs (más simple con Spring Boot)
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView,
            meta: { guestOnly: true } // Solo para no autenticados
        },
        {
            path: '/productos',
            name: 'productos',
            component: ProductosView
        },
        {
            path: '/servicios',
            name: 'servicios',
            component: ServiciosView
        },
        {
            path: '/saldo',
            name: 'saldo',
            component: SaldoView,
            meta: { requiresAuth: true }
        },
        {
            path: '/informes',
            name: 'informes',
            component: InformesView,
            meta: { requiresAuth: true, requiresAdmin: true }
        },
        {
            path: '/despacho',
            name: 'despacho',
            component: DespachoView,
            meta: { requiresAuth: true, requiresAdmin: true }
        },
        {
            path: '/formulario-producto',
            name: 'formulario-producto',
            component: FormularioProductoView,
            meta: { requiresAuth: true, requiresAdmin: true }
        },
        {
            path: '/formulario-servicio',
            name: 'formulario-servicio',
            component: FormularioServicioView,
            meta: { requiresAuth: true, requiresAdmin: true }
        },
        {
            path: '*',
            redirect: '/' // Redirigir rutas no encontradas al home
        }
    ]
});

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