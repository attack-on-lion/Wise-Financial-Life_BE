package com.wisespendinglife.wise_spending_life.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 필요 시 "/api/**"로 좁혀도 됨
                .allowedOrigins(
                        "https://slgmslgm.com",
                        "https://www.slgmslgm.com",
                        "http://localhost:3000",
                        "http://localhost:5173"
                )
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Location","Content-Disposition")
                .maxAge(3600);
    }

}
