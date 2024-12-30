package org.example.todo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.swing.plaf.PanelUI;
import java.util.Arrays;

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
                .cors().configurationSource(corsConfigurationSource()) // CORS 설정 적용
                .and()
                .csrf().disable()
                .authorizeHttpRequests((request) -> {
                    request.requestMatchers("/check/**").hasRole("USER");
                    request.requestMatchers("/todo/**").hasRole("USER");
                    request.requestMatchers("/message/**").hasRole("USER");
                    request.anyRequest().permitAll(); // 나머지 경로는 인증 없이 접근 가능
                })
                .formLogin((login) -> {
                    login.loginProcessingUrl("/login");
                    login.usernameParameter("userId");
                    login.defaultSuccessUrl("/loginOk", true);
                })
                .logout((logout) -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/logoutok");
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                })
                .exceptionHandling((exceptions) -> {
                    exceptions
                            .authenticationEntryPoint((request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                            .accessDeniedHandler((request, response, accessDeniedException) ->
                                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"));
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // 세션이 필요할 때만 생성
                })
                .securityContext((context) -> {
                    context.securityContextRepository(new HttpSessionSecurityContextRepository());
                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://localhost:3000",
                "https://6772552a00bb2638344f7050--takehub.netlify.app"
        ));
        configuration.addAllowedMethod("*"); // 모든 HTTP 메소드 허용 (GET, POST 등)
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키와 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 적용

        return source;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();

        // 쿠키 설정
        cookieSerializer.setCookiePath("/"); // 쿠키 경로
        cookieSerializer.setCookieName("SESSION"); // 쿠키 이름
        cookieSerializer.setUseSecureCookie(true); // HTTPS에서만 쿠키 사용
        cookieSerializer.setUseHttpOnlyCookie(true); // JavaScript에서 쿠키에 접근하지 못하게 설정
        cookieSerializer.setSameSite("None"); // Cross-site 요청에서 쿠키가 전송되도록 설정

        return cookieSerializer;
    }



//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .cors()
//                .and()
//                .csrf().disable()
//                .authorizeHttpRequests((request) -> {
//                    request.requestMatchers("/check/**").hasRole("USER");
//                    request.requestMatchers("/todo/**").hasRole("USER");
//                    request.requestMatchers("/message/**").hasRole("USER");
//                    request.anyRequest().permitAll(); // 나머지 경로는 인증 없이 접근 가능
//                })
//                .formLogin((login) -> {
//                    login.loginProcessingUrl("/login");
//                    login.usernameParameter("userId");
//                    login.defaultSuccessUrl("/loginOk", true);
//                })
//                .logout((logout)->{
//                    logout.logoutUrl("/logout");
//                    logout.logoutSuccessUrl("/logoutok");
//                    logout.invalidateHttpSession(true);
//                    logout.deleteCookies("JSESSIONID");
//                })
//                .exceptionHandling((exceptions) -> {
//                    exceptions
//                            .authenticationEntryPoint((request, response, authException) ->
//                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
//                            .accessDeniedHandler((request, response, accessDeniedException) ->
//                                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"));
//                })
//
//                .build();
//    }

//    @Bean
//    public ServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers(connector -> {
//            connector.setProperty("server.servlet.session.cookie.sameSite", "None");  // 쿠키 SameSite 설정
//            connector.setProperty("server.servlet.session.cookie.secure", "true");  // 쿠키 보안을 위한 설정
//        });
//        return factory;
//    }


}

