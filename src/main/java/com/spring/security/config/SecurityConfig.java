package com.spring.security.config;


import com.spring.security.utils.AppConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> {
                    authorize.anyRequest().authenticated();
                })
                .formLogin(form -> form.permitAll().successHandler((request, response, authentication) -> {
                    response.sendRedirect("/api/user");
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

        return new InMemoryUserDetailsManager(admin);
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}