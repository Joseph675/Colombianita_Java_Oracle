package com.colombianita.Colombianita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean para encriptar/desencriptar contraseñas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desactivar CSRF por ser una API REST sin estado
            .csrf(csrf -> csrf.disable()) 
            // Habilitar CORS basándonos en las anotaciones @CrossOrigin de tus controladores
            .cors(cors -> cors.configure(http)) 
            // Configurar política de sesión sin estado (Stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Dejamos pública la ruta de login
                .requestMatchers("/api/auth/**").permitAll()
                
                // Por AHORA permitimos el resto de rutas para no romper tus otros controladores (Productos, Usuarios, etc).
                // Más adelante, cuando agregues el Filtro JWT, cambiaremos esto a: .anyRequest().authenticated()
                .anyRequest().permitAll()
            );
            
        return http.build();
    }
}