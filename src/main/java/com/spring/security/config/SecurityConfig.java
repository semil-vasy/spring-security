package com.spring.security.config;


import com.spring.security.security.CustomUserDetailService;
import com.spring.security.utils.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers("/admin/**").hasAuthority(AppConstant.ROLE_ADMIN)
                            .requestMatchers("/user/**").hasAnyAuthority(AppConstant.ROLE_USER,AppConstant.ROLE_ADMIN)
                            .anyRequest().permitAll();
                })
                .formLogin(form -> form
                        .successHandler((request, response, authentication) -> {
                    boolean isAdmin = authentication.getAuthorities().stream()
                            .anyMatch(r -> r.getAuthority().equals(AppConstant.ROLE_ADMIN));
                    if(isAdmin){
                        response.sendRedirect("/admin");
                    }else{
                        response.sendRedirect("/user");
                    }
                }));
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}