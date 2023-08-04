package wowmarket.wow_server.global.jwt;


import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import wowmarket.wow_server.login.service.CustomUserDetailsService;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.token.key}")
    private String secretKey;

    // 토큰 유효시간 1시간
    private final long tokenValidTime = 60 * 60 * 1000L;
    // refresh token 유효시간 7일
    private final long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;

    // 객체 초기화, secretKey 를 Base64로 인코딩
    @PostConstruct //스프링이 빈을 생성한 후 자동으로 호출
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT AccessToken 생성
    public String createAccessToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위
        claims.put("role", role); // 정보는 key/value 쌍으로 저장됩니다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘
                // signature 에 들어갈 secret 값 세팅
                .compact();
    }

    //Refresh Token 생성
    public String createRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회  DB 조회 1번 일어남
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보(이메일) 추출
    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져오는 메서드 "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("X-ACCESS-TOKEN");
    }
    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("X-REFRESH-TOKEN");
    }

//     access token의 만료일자 가져오기
     public Long getExpiration(String accessToken){
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
     }

    // Access Token의 유효성 + 만료일자 확인
    public boolean validateAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
            return claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
