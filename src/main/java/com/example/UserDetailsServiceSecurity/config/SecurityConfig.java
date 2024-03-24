package com.example.UserDetailsServiceSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizeHttpRequest ->
                authorizeHttpRequest
                        .requestMatchers("/home", "register", "/saveUser").permitAll()
                        .requestMatchers("/welcome").authenticated()
                        .requestMatchers("/admin").hasAuthority("Admin")
                        .requestMatchers("/mgr").hasAuthority("Manager")
                        .requestMatchers("/emp").hasAuthority("Employee")
                        .requestMatchers("/hr").hasAuthority("HR")
                        .requestMatchers("/common").hasAuthority("Employee, Manager, Admin")
                        .anyRequest().authenticated()

                        )
                .exceptionHandling(ex -> ex.accessDeniedPage("/accessDenied"))
                .formLogin(f -> f.loginPage("/welcome"))
                .logout(l -> new AntPathRequestMatcher("/logout"))
                .authenticationProvider(authenticationProvider());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }
}
