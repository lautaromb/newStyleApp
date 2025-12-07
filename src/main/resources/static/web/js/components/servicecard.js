Vue.component('service-card', {
    props: ['servicio'],
    template: `
        <div class="col-12 col-sm-6 col-md-6 col-lg-4 col-xl-3" style="display: flex; justify-content: center; margin-bottom: 2rem;">
            <div class="card" style="width: 20rem;">
                <img :src="servicio.imagenCard || 'https://i.imgur.com/FH8h7Un.jpg'" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">{{ servicio.nombre }}</h5>
                    <p class="card-text">{{ servicio.descripcion }}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item text-success fw-bold">\${{ servicio.valor }}</li>
                </ul>
                <div class="card-body">
                    <button 
                        class="btn btn-primary w-100" 
                        @click="$emit('agregar', servicio)">
                        Agregar al carrito
                    </button>
                </div>
            </div>
        </div>
    `
});