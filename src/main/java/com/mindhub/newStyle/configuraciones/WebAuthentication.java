package com.mindhub.newStyle.configuraciones;


import com.mindhub.newStyle.modelos.Cliente;
import com.mindhub.newStyle.repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {

            Cliente cliente = repositorioCliente.getByEmail(inputName);


            if (cliente != null) {
                //admin

                if (cliente.getEmail().contains("@admin") ){
                    return new User(cliente.getEmail(), cliente.getPassword(),
                    AuthorityUtils.createAuthorityList("ADMIN"));
                }

                //client
                return new User(cliente.getEmail(), cliente.getPassword(),

                        AuthorityUtils.createAuthorityList("CLIENTE"));

            } else {

                throw new UsernameNotFoundException("Cliente desconocido: " + inputName);

            }

        });

    }



}

