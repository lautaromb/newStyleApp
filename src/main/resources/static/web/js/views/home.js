const HomeView = {
    template: `
        <main>
            <!-- GIF IMAGEN PRINCIPAL -->
            <div>
                <img src="assets/banner.gif" alt="" style="width: 100%;">
            </div>

            <!-- CAROUSEL CARDS SERVICIOS -->
            <section style="margin-top: 1.5rem;">
                <div class="container-fluid">
                    <hr>
                    <div class="d-flex align-items-center bd-highlight">
                        <div class="p-2 w-100 bd-highlight">
                            <h3>Servicios</h3>
                        </div>
                        <div class="p-2 flex-shrink-1 bd-highlight" style="text-align: center;">
                            <router-link to="/servicios" style="text-decoration: none;">Ver Servicios</router-link>
                        </div>
                    </div>
                    <hr>
                    
                    <!-- Aquí puedes agregar un carousel o cards destacadas -->
                    <div class="row">
                        <div class="col-md-12">
                            <p class="text-center">Descubre nuestros servicios profesionales de belleza</p>
                        </div>
                    </div>
                </div>
            </section>

            <!-- CAROUSEL CARDS PRODUCTOS -->
            <section style="margin-top: 1.5rem;">
                <div class="container-fluid" style="padding-bottom: 3rem; padding-top: 0.5rem;">
                    <hr>
                    <div class="d-flex align-items-center bd-highlight">
                        <div class="p-2 w-100 bd-highlight">
                            <h3>Productos</h3>
                        </div>
                        <div class="p-2 flex-shrink-1 bd-highlight" style="text-align: center;">
                            <router-link to="/productos" style="text-decoration: none;">Ver Productos</router-link>
                        </div>
                    </div>
                    <hr>
                    
                    <div class="row">
                        <div class="col-md-12">
                            <p class="text-center">Los mejores productos para el cuidado de tu cabello</p>
                        </div>
                    </div>
                </div>
            </section>

            <div class="p-2 link-inicio">
                <div class="div-link-inicio">
                    <a href="#app">Volver Arriba</a>
                </div>
            </div>
        </main>
    `
};