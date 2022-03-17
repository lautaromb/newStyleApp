var contcarrito = 0;
const app = new Vue({
	el: '#app',
	data: {
		total: null,
        servicios:[],
		carrito: [],
		contador : 1
	},
	created: function(){
        this.loadData();
		let datosDB = JSON.parse(localStorage.getItem('carrito-vue'));
        
		let datosDB2 = JSON.parse(localStorage.getItem('contador-vue'));
		if(datosDB === null){
			this.carrito = [];
		}else{
			//Se obtienen los valores guardados en memoria local.
			this.carrito = datosDB;
			contcarrito = datosDB2;
			//Se ejecutan las funciones para restablecer la pagina.
			this.calcular();
			
		}
	},
	methods: {
        loadData: function () {
            axios.get("/api/servicio").then((response) => {
              console.log(response.data);
              this.servicios = response.data;
              
              console.log(this.servicios);
      
            });
          },
		agregarCarrito: function(index){
			//Se valida si el carrito esta vacio.
			if(!this.carrito.length){
				//De ser así agrega el producto con su cantidad inicial.
				this.servicios[index].cantidad = 1;
				this.carrito.push(this.servicios[index]);
				contcarrito++;
				this.guardarLocal();
			}else{
				//Ciclo para recorrer servicios del carrito.
				for (var i = 0; i < this.carrito.length; i++) {
					//Si el producto agregado existe, solo aumenta su cantidad y termina ciclo.
					if(this.carrito[i].nombre == this.servicios[index].nombre){
						this.carrito[i].cantidad++;
						this.guardarLocal();
						break;
					//En la ultima iteracion, si el producto no existe, lo agrega y termina ciclo.
					}else if(i == (this.carrito.length-1)){
						this.servicios[index].cantidad = 1;
						this.carrito.push(this.servicios[index]);
						contcarrito++;
						this.guardarLocal();
						break;
					}
				}
			}
			//Se vuelve a cargar en pantalla los servicios del carrito.
			this.$forceUpdate();
			// Se reinician los importes del carrito.
			this.calcular();
		},
		quitarProducto: function (index){
			this.carrito.splice(index,1);
			contcarrito--;
			this.calcular();
			this.$forceUpdate();
			this.guardarLocal();
			
		},
		calcular: function(){
			var total=0;
			
			for (var i = 0; i < this.carrito.length; i++) {
				total = (this.carrito[i].cantidad * this.carrito[i].valor) + total;
			}
			
			this.total = total.toFixed(2);
			
		},
		guardarLocal: function(){
			//Se guardan las variables en memoria local.
			localStorage.setItem('carrito-vue', JSON.stringify(this.carrito));
			localStorage.setItem('contador-vue', JSON.stringify(contcarrito));
		},cerrarSesion() {
			axios
			  .post("/api/logout")
			  .then((response) => console.log("sesion cerrada!!!"))
	  
			  .then((response) => {
				console.log("signed in!!!");
				return (window.location.href = "/web/html/index.html");
			  })
	  
			  .catch((e) => {
				console.log(e);
			  });
		  }
	},
    
});
