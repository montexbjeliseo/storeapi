package com.mtxbjls.storeapi.security.config;

import com.mtxbjls.storeapi.exceptions.CustomAccessDeniedHandler;
import com.mtxbjls.storeapi.exceptions.CustomAuthenticationEntryPoint;
import com.mtxbjls.storeapi.security.filters.JwtTokenRequestFilter;
import com.mtxbjls.storeapi.utils.Constants.Endpoints;
import com.mtxbjls.storeapi.utils.Constants.PathVariables;
import com.mtxbjls.storeapi.utils.Constants.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig {

    @Autowired
    private JwtTokenRequestFilter jwtTokenRequestFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(handling -> handling
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(requests
                        -> requests
                        .requestMatchers(Endpoints.DOCS).permitAll()
                        .requestMatchers(Endpoints.SWAGGER_CONFIG).permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                Endpoints.AUTH + Endpoints.LOGIN,
                                Endpoints.AUTH + Endpoints.REGISTER).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                Endpoints.CATEGORIES,
                                Endpoints.CATEGORIES + PathVariables.ID,
                                Endpoints.PRODUCTS,
                                Endpoints.PRODUCTS + PathVariables.ID,
                                Endpoints.FILES + PathVariables.FILENAME).permitAll()
                        .requestMatchers(
                                HttpMethod.POST, 
                                Endpoints.CATEGORIES,
                                Endpoints.PRODUCTS,
                                Endpoints.FILES + Endpoints.UPLOAD).hasRole(Roles.ADMIN)
                        .requestMatchers(
                                HttpMethod.PATCH, 
                                Endpoints.CATEGORIES, Endpoints.PRODUCTS).hasAuthority(Roles.ADMIN)
                        .requestMatchers(
                                HttpMethod.DELETE, 
                                Endpoints.CATEGORIES, Endpoints.PRODUCTS).hasAuthority(Roles.ADMIN)
        
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtTokenRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }
    
    @Bean
    @Value("${admin.email}")
    public String adminEmail(String email){
        return email;
    }
    
    @Bean
    @Value("${admin.password}")
    public String adminPassword(String password){
        return password;
    }
}
