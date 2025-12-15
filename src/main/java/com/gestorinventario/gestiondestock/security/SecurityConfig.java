package com.gestorinventario.gestiondestock.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Configuración de la seguridad de las peticiones HTTP (autorización de URLs)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests

                // Permite el acceso a la página de login personalizada (si la creamos)
                .requestMatchers("/login").permitAll()
                // La página principal y la búsqueda requieren CUALQUIER usuario autenticado (USER o ADMIN)
                .requestMatchers("/").authenticated() 
                // Las rutas de crear, editar y eliminar requieren el rol ADMIN
                .requestMatchers("/proveedores/nuevo", "/proveedores/guardar", 
                                 "/productos/nuevo", "/productos/guardar",
                                 "/productos/editar/**", "/productos/eliminar/**").hasRole("ADMIN")
                // Restringe todas las demás rutas
                .anyRequest().authenticated() 
            )
            // Habilita el formulario de inicio de sesión por defecto de Spring Security
            .formLogin((form) -> form
                // Especifica que use la URL /login para mostrar el formulario
                .loginPage("/login")
                .permitAll()
            )
            // Habilita el cierre de sesión
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // 2. Configuración de usuarios en memoria (Temporal, para probar la seguridad)
    @Bean
    public UserDetailsService userDetailsService() {

        // Usuario 1: ADMIN (Acceso total)
        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("password")
            .roles("ADMIN")
            .build();

        // Usuario 2: USER (Solo ver y buscar)
        UserDetails user = User.withDefaultPasswordEncoder() // ¡OJO! Solo para desarrollo
            .username("user")
            .password("password")
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
    
}
