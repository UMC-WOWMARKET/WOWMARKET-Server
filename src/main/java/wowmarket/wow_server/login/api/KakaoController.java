package wowmarket.wow_server.login.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.login.dto.KakaoResponseDto;
import wowmarket.wow_server.login.service.KakaoAPIService;

import java.util.HashMap;

@RestController
@RequestMapping("/wowmarket/users/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoAPIService kakaoAPI;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public KakaoResponseDto login(@RequestParam("code") String code) {
        System.out.println("\n[login Controller] 로그인 로직 실행\n");
        System.out.println("[login Controller] 토큰 받기 요청에 필요한 인가 코드 : " + code + "\n");

        String access_token = kakaoAPI.getAccessToken(code);
        System.out.println("\n[login Controller] 카카오 액세스 토큰 값 : " + access_token + "\n");
//        session.setAttribute("access_token", access_token);

//        KakaoResponseDto kakaoResponseDto = kakaoAPI.getUserInfo(access_token);
//        System.out.println("\n[login Controller] HashMap<String, Object> userInfo : " + userInfo);
//        session.setAttribute("user_email", userInfo.get("email"));

//        System.out.println("\n[login Controller] 로그인 성공!\n");
//        return ResponseEntity.ok().body(userService.signIn(request)).getBody();
        return ResponseEntity.ok().body(kakaoAPI.getUserInfo(access_token)).getBody();
//        return kakaoResponseDto;
    }
}
