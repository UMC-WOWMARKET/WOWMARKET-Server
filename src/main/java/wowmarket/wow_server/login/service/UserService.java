package wowmarket.wow_server.login.service;

import jakarta.servlet.http.HttpServletRequest;
import wowmarket.wow_server.login.dto.TokenResponseDto;
import wowmarket.wow_server.login.dto.UserSignInRequestDto;
import wowmarket.wow_server.login.dto.UserSignUpRequestDto;

public interface UserService {
    public Long signUp(UserSignUpRequestDto requestDto) throws Exception;

    public TokenResponseDto signIn(UserSignInRequestDto requestDto) throws Exception;

    public void sendMailAndChangePassword(String email);
    public Long updatePassword(String str, String email, Boolean temp);
    public String getTempPassword();


    public TokenResponseDto issueAccessToken(HttpServletRequest request);
}
