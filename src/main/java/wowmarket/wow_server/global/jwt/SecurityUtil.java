package wowmarket.wow_server.global.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    /**
     * Security Context Holder를 통해 현재 인증(Authentication) 정보를 가져옴
     */
    public static String getLoginUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}