// Configuración global de Axios
import axios from 'axios';

// Interceptor para redirigir en caso de error 401
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      console.log('Sesión expirada, redirigiendo al login...');
      // Limpiar datos de sesión
      localStorage.removeItem('user');
      localStorage.removeItem('token');
      // Redirigir a login
      window.location.href = '/web/html/index.html';
    }
    return Promise.reject(error);
  }
);

export default axios;