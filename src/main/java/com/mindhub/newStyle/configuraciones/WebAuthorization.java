package com.mindhub.newStyle.configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // ============ RECURSOS PÚBLICOS (CSS, JS, IMÁGENES) ============
                .antMatchers("/web/css/**", "/web/js/**", "/web/assets/**").permitAll()

                // ============ PÁGINAS PÚBLICAS ============
                .antMatchers("/web/html/index.html",
                        "/web/html/productos.html",
                        "/web/html/servicios.html",
                        "/web/html/home.html").permitAll()

                // ============ PÁGINAS SOLO PARA ADMIN ============
                .antMatchers("/web/html/formularioProducto.html",
                        "/web/html/formularioServicio.html",
                        "/web/html/informes.html",
                        "/web/html/despacho.html").hasAuthority("ADMIN")

                // ============ PÁGINAS PARA USUARIOS AUTENTICADOS ============
                .antMatchers("/web/html/saldo.html",
                        "/web/html/contactanos.html").authenticated()

                // ============ APIs SOLO PARA ADMIN ============
                .antMatchers("/api/informes/**", "/api/despacho/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/producto", "/api/servicio").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/producto/**", "/api/servicio/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/producto/**", "/api/servicio/**").hasAuthority("ADMIN")

                // ============ APIs PARA USUARIOS AUTENTICADOS ============
                .antMatchers("/api/cliente/current").authenticated()
                .antMatchers("/api/cliente/saldo/**").authenticated()
                .antMatchers("/api/compra", "/api/compras/**").authenticated()

                // ============ APIs PÚBLICAS ============
                .antMatchers(HttpMethod.POST, "/api/clientes").permitAll()
                .antMatchers(HttpMethod.GET, "/api/producto", "/api/servicio").permitAll()

                // ============ CUALQUIER OTRA PETICIÓN REQUIERE AUTENTICACIÓN ============
                .anyRequest().authenticated();

        // ============ CONFIGURACIÓN DE LOGIN ============
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login")
                .permitAll();

        // ============ CONFIGURACIÓN DE LOGOUT ============
        http.logout()
                .logoutUrl("/api/logout")
                .permitAll();

        // ============ DESACTIVAR CSRF (para desarrollo) ============
        http.csrf().disable();

        // ============ DESACTIVAR FRAME OPTIONS (para h2-console) ============
        http.headers().frameOptions().disable();

        // ============ MANEJO DE ERRORES DE AUTENTICACIÓN ============
        http.exceptionHandling()
                .authenticationEntryPoint((req, res, exc) -> {
                    String requestURI = req.getRequestURI();

                    // Si está intentando acceder a una página HTML protegida, redirigir
                    if (requestURI.contains("/web/html/") && !requestURI.contains("index.html")) {
                        res.sendRedirect("/web/html/index.html");
                    } else {
                        // Para APIs, devolver 401
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                });

        // ============ LIMPIEZA DESPUÉS DE LOGIN EXITOSO ============
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // ============ RESPUESTA DESPUÉS DE LOGOUT ============
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}