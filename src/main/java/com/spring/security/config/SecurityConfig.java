package com.spring.security.config;


import com.spring.security.utils.AppConstant;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.security.admin.name}")
    String adminName;
    @Value("${spring.security.admin.password}")
    String adminPassword;
    @Value("${spring.security.user.name}")
    String userName;
    @Value("${spring.security.user.password}")
    String userPassword;
    @Value("${spring.security.roles.user}")
    String roleUser;
    @Value("${spring.security.roles.admin}")
    String roleAdmin;

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
                .username(adminName)
                .password(passwordEncoder().encode(adminPassword))
                .roles(roleAdmin)
                .build();

        UserDetails user = User.builder()
                .username(userName)
                .password(passwordEncoder().encode(userPassword))
                .roles(roleUser)
                .build();

        return new InMemoryUserDetailsManager(admin,user);
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}