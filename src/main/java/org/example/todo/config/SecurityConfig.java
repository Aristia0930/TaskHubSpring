package org.example.todo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.plaf.PanelUI;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests((request) -> {
                    request.requestMatchers("/check/**").hasRole("USER");
                    request.anyRequest().permitAll(); // 나머지 경로는 인증 없이 접근 가능
                })
                .formLogin((login) -> {
                    login.loginProcessingUrl("/login");
                    login.usernameParameter("userId");
                    login.defaultSuccessUrl("/loginOk", true);
                })
                .exceptionHandling((exceptions) -> {
                    exceptions
                            .authenticationEntryPoint((request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                            .accessDeniedHandler((request, response, accessDeniedException) ->
                                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"));
                })
                .build();
    }
}

