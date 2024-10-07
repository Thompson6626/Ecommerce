package com.ecommerce.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig{

    private static final String[] WHITE_LIST = {
            "/auth/**",
            // Documentation
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };
    private static final String[] ORDER_USERS = {
            "/orders/history",
            "/orders/{orderId}/cancel",
            "/orders/{orderId}/return"
    };
    private static final String[] ORDER_ADMINS = {
            "/orders/{orderId}/shipped",
            "/orders/{orderId}/delivered"
    };

        private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(ORDER_USERS).hasAnyRole("BUYER","SELLER") 
                        .requestMatchers(ORDER_ADMINS).hasRole("ADMIN") 
                        .requestMatchers("/orders/**").authenticated()  
                        .requestMatchers(GET,"/category").permitAll()
                        .requestMatchers("/category/**").hasRole("ADMIN")
                        .requestMatchers("/reviews").authenticated()
                        .requestMatchers(POST,"/products").hasAnyRole("SELLER","ADMIN")
                        .requestMatchers(PUT,"/products/id/**").hasAnyRole("SELLER")
                        .requestMatchers(DELETE,"/products/id/**").hasAnyRole("SELLER","ADMIN")
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers(WHITE_LIST).permitAll()          // Public paths
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
