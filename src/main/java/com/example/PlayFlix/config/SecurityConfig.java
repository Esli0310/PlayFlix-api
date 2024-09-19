package com.example.PlayFlix.config;

import com.example.PlayFlix.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/auth/**").permitAll()
                        //contenido
                                .requestMatchers(HttpMethod.GET, "/api/contenidos/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/contenidos/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/contenidos/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/contenidos/{id}").permitAll()
                        //Clasificaciones
                        .requestMatchers(HttpMethod.GET, "/api/contenidos/", "/api/clasificaciones/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contenidos/","/api/clasificaciones/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/contenidos/","/api/clasificaciones/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/contenidos/","/api/clasificaciones/{id}").permitAll()
                        //favoritos
//                        .requestMatchers(HttpMethod.GET, "/images/cursos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/contenidos/","/api/favoritos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contenidos/","api/favoritos/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/contenidos/","/api/favoritos/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/contenidos/","/api/favoritos/{id}").permitAll()
                        //comentarios
                                .requestMatchers(HttpMethod.GET, "/api/contenidos/","/api/comentarios/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/contenidos/","api/comentarios/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/contenidos/","/api/favoritos/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/contenidos/","/api/favoritos/{id}").permitAll()
                        //categoria-contenidos
                                .requestMatchers(HttpMethod.GET, "/api/contenidos/","/api/categoria-contenidos/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/contenidos/","/api/categoria-contenidos/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/contenidos/","/api/categoria-contenidos/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/contenidos/","/api/categoria-contenidos/{id}").permitAll()

                        //login
                                .requestMatchers(HttpMethod.POST, "/auth/login/**").permitAll()
                        //registrar
                                .requestMatchers(HttpMethod.POST, "/auth/register/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    };
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173/"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
};

