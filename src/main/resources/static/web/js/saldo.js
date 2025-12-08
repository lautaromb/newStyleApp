const app = new Vue({
    el: '#app',
    data: {
        saldo: 0,
        montoAgregar: '',
        emailDestino: '',
        montoTransferir: '',
        montoRemover: ''
    },
    created() {
        this.cargarSaldo();
    },
    methods: {
        cargarSaldo() {
            axios.get('/api/cliente/saldo')
                .then(response => {
                    this.saldo = response.data;
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
        agregarSaldo() {
            if (!this.montoAgregar || this.montoAgregar <= 0) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Monto inválido',
                    text: 'Ingresa un monto mayor a 0',
                    timer: 2000
                });
                return;
            }

            const formData = new URLSearchParams();
            formData.append('monto', this.montoAgregar);

            axios.post('/api/cliente/saldo/agregar', formData)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Dinero agregado',
                        text: response.data,
                        timer: 2000
                    });
                    this.montoAgregar = '';
                    this.cargarSaldo();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.response?.data || 'No se pudo agregar el dinero',
                        timer: 2000
                    });
                });
        },
        transferirSaldo() {
            if (!this.emailDestino || !this.montoTransferir || this.montoTransferir <= 0) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Datos incompletos',
                    text: 'Completa todos los campos correctamente',
                    timer: 2000
                });
                return;
            }

            const formData = new URLSearchParams();
            formData.append('emailDestino', this.emailDestino);
            formData.append('monto', this.montoTransferir);

            axios.post('/api/cliente/saldo/transferir', formData)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Transferencia exitosa',
                        text: response.data,
                        timer: 2000
                    });
                    this.emailDestino = '';
                    this.montoTransferir = '';
                    this.cargarSaldo();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.response?.data || 'No se pudo transferir',
                        timer: 2500
                    });
                });
        },
        removerSaldo() {
            if (!this.montoRemover || this.montoRemover <= 0) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Monto inválido',
                    text: 'Ingresa un monto mayor a 0',
                    timer: 2000
                });
                return;
            }

            const formData = new URLSearchParams();
            formData.append('monto', this.montoRemover);

            axios.post('/api/cliente/saldo/remover', formData)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Retiro exitoso',
                        text: response.data,
                        timer: 2000
                    });
                    this.montoRemover = '';
                    this.cargarSaldo();
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.response?.data || 'No se pudo retirar',
                        timer: 2000
                    });
                });
        }
    }
});