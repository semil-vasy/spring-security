package com.spring.security.config;


import com.spring.security.utils.AppConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers("/admin/**")
                            .hasAuthority("ROLE_ADMIN")
                            .requestMatchers("/user/**")
                            .hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                            .anyRequest().authenticated();
                })
                .formLogin(form -> form.permitAll().successHandler((request, response, authentication) -> {
                    boolean isAdmin = authentication.getAuthorities().stream()
                            .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
                    if(isAdmin){
                        response.sendRedirect("/admin");
                    }else{
                        response.sendRedirect("/user");
                    }
                }));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username(AppConstant.ADMIN_NAME)
                .password(passwordEncoder().encode(AppConstant.ADMIN_PASSWORD))
                .roles(AppConstant.ROLE_ADMIN)
                .build();

        UserDetails user = User.builder()
                .username(AppConstant.USER_NAME)
                .password(passwordEncoder().encode(AppConstant.USER_PASSWORD))
                .roles(AppConstant.ROLE_USER)
                .build();

        return new InMemoryUserDetailsManager(admin,user);
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}