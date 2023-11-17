package com.example.springBlog.settings.security;


import com.example.springBlog.settings.security.jwt.JwtAuthenticationEntryPoint;
import com.example.springBlog.settings.security.jwt.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService jwtUserDetailsService;
    private JwtRequestFilter jwtRequestFilter;


    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET,
                                "/error/**",
                                "/photos/**",
                                "/user/{id}",
                                "/user//{id}/authors",
                                "/user/{id}/followers",
                                "/post"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/signup",
                                "/signin"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/post",
                                "/post/{id}/status",
                                "/user/follow/{id}"
                        ).authenticated()
                        .requestMatchers(HttpMethod.GET, "/post/liked_posts"
                        ).authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/post/{id}",
                                "/user/edit").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/post/{id}",
                                "/user/{id}").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                );

        http.exceptionHandling((exception) -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedPage("/error/forbidden"));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}