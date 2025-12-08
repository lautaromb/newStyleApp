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
        http.csrf().disable();

        http.authorizeRequests()
                // permitir recursos estáticos primero
                .antMatchers("/web/js/**", "/web/css/**", "/web/assets/**", "/web/index.html", "/web/*.html").permitAll()

                // APIs públicas
                .antMatchers(HttpMethod.POST, "/api/clientes").permitAll()
                .antMatchers(HttpMethod.GET, "/api/producto/**", "/api/servicio/**").permitAll()

                // APIs que requieren autenticación
                .antMatchers("/api/cliente/current", "/api/compra/**", "/api/compras/**", "/api/cliente/saldo/**").authenticated()

                // reglas admin (ajustar si es necesario)
                .antMatchers("/api/informes/**", "/api/despacho/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()
                .and()
            .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/web/index.html")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}