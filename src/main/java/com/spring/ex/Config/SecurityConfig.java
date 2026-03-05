/**
 * File-level: Spring Security configuration that registers the JWT filter and sets basic security rules.
 */

package com.spring.ex.Config;

import com.spring.ex.security.JwtAuthenticationFilter;
import com.spring.ex.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig registers security beans and inserts the JwtAuthenticationFilter into the filter chain.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    /**
     * Creates and configures the SecurityFilterChain.
     *
     * @param http    HttpSecurity builder
     * @param jwtUtil Jwt utility used by JwtAuthenticationFilter
     * @return configured SecurityFilterChain
     * @throws Exception if HttpSecurity.build() fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil)
            throws Exception {
        // Instantiate the filter with the JwtUtil (ensure JwtUtil is a @Component or @Bean)
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil);


        http
                // stateless REST style
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // public endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/public/**").permitAll()
                        .anyRequest().authenticated()
                );

        // Add JWT filter before Spring Security's UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
