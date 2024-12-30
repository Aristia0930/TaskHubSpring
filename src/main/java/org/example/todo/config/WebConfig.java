package org.example.todo.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
                .allowedOrigins(
                        "http://localhost:3000", // 로컬 React 클라이언트
                        "https://localhost:3000", // 로컬 HTTPS 테스트 (필요한 경우)
                        "https://6772552a00bb2638344f7050--takehub.netlify.app" // Netlify 배포 주소
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("Authorization", "Content-Type") // 허용할 헤더
                .allowCredentials(true); // 자격 증명(쿠키) 허용
    }

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        // HTTPS 환경이 필요한 경우 추가 설정
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        // HTTPS 설정 추가 시, 인증서 경로 등을 설정할 수 있습니다.
        return factory;
    }
}
