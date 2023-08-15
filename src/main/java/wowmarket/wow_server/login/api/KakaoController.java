package wowmarket.wow_server.login.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.login.dto.KakaoResponseDto;
import wowmarket.wow_server.login.service.KakaoAPIService;

@RestController
@RequestMapping("/users/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoAPIService kakaoAPI;

    @PostMapping(value = "/login")
    public KakaoResponseDto login(@RequestParam("code") String code) {
        System.out.println("\n[login Controller] 카카오 로그인 \n");
        String access_token = kakaoAPI.getAccessToken(code);
        return kakaoAPI.getUserInfo(access_token);
    }
}
