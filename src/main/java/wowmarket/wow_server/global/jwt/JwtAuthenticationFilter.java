package wowmarket.wow_server.global.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 토큰의 인증정보를 검사한 후 Security Context에 저장
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtAuthenticationProvider;
    private final RedisTemplate redisTemplate;

    public JwtAuthenticationFilter(JwtTokenProvider provider, RedisTemplate template) {
        this.jwtAuthenticationProvider = provider;
        this.redisTemplate = template;
    }

    /**
     * 요청이 들어올 때마다 실행되는 메서드로,
     * 요청의 헤더에서 JWT 토큰을 추출하여 유효성을 검사하고,
     * 유효한 토큰이면 인증 정보를 추출하여 현재 보안 컨텍스트에 설정
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtAuthenticationProvider.resolveAccessToken(request); //request 헤더에서 토큰을 가져옴

        if (token != null && jwtAuthenticationProvider.validateAccessToken(token)) {
            //logout된 jwt 검증 로직, 추후에 추가 예정
//            String isLogout = (String)redisTemplate.opsForValue().get(token);
//            if(ObjectUtils.isEmpty(isLogout)){
//            }

            //유효한 토큰이면 JwtTokenProvider를 통해 Authentication 객체를 생성
            Authentication authentication = jwtAuthenticationProvider.getAuthentication(token);

            // 현재 스레드의 Security Context에 인증 정보를 저장 -> 해당 요청을 처리하는 동안 인증된 사용자로서 권한이 부여
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }

        filterChain.doFilter(request, response);
    }

}
