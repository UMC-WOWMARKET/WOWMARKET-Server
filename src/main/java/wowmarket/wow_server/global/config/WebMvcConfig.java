package wowmarket.wow_server.global.config;

import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS","TRACE") //허용할 메소드
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

}
