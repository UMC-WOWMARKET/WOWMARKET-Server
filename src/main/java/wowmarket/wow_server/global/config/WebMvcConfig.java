package wowmarket.wow_server.global.config;

import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3002") // 이 설정 없으면 모든 origin에 대해 허용
//                .allowedMethods("*") // 기타 설정
//                .allowedHeaders("*");
                .allowedMethods(ALLOWED_METHOD_NAMES.split(",")) //허용할 메소드
                .exposedHeaders(HttpHeaders.LOCATION); // 서버에서 반환해 줄 헤더
    }

//    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
//
//    @Override
//    public void addCorsMappings(final CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods(ALLOWED_METHOD_NAMES.split(",")) //허용할 메소드
//                .exposedHeaders(HttpHeaders.LOCATION); // 서버에서 반환해 줄 헤더
//    }
}
