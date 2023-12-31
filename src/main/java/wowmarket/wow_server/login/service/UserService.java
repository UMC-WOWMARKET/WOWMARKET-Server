package wowmarket.wow_server.login.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.login.dto.TokenResponseDto;
import wowmarket.wow_server.login.dto.UserSignInRequestDto;
import wowmarket.wow_server.login.dto.UserSignUpRequestDto;

public interface UserService {
    public Long signUp(UserSignUpRequestDto requestDto) throws Exception;

    public TokenResponseDto signIn(UserSignInRequestDto requestDto) throws Exception;

    public void sendMailAndChangePassword(String email);
    public Long updatePassword(String str, String email, Boolean temp);
    public String getTempPassword();

    public void logout(String token, User user);
    public TokenResponseDto issueAccessToken(HttpServletRequest request);
}
