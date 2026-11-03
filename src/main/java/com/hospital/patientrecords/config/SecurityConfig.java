package com.hospital.patientrecords.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        var admin = User.withUsername("admin")
                .password(passwordEncoder.encode("Admin@123"))
                .roles("ADMIN")
                .build();

        var staff = User.withUsername("staff")
                .password(passwordEncoder.encode("Staff@123"))
                .roles("STAFF")
                .build();

        return new InMemoryUserDetailsManager(admin, staff);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/error", "/access-denied").permitAll()
                .requestMatchers("/patients/delete/**").hasRole("ADMIN")
                .requestMatchers("/patients/**").hasAnyRole("ADMIN", "STAFF")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/patients", true)
                .permitAll()
            )
            .exceptionHandling(exception -> exception
            	    .accessDeniedHandler((request, response, accessDeniedException) -> {
            	        response.sendRedirect("/access-denied");
            	    })
            	)
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}