package wowmarket.wow_server.login.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wowmarket.wow_server.login.service.KakaoAPIService;

import java.util.HashMap;

@Controller
public class KakaoController {
    @Autowired
    KakaoAPIService kakaoAPI;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/wowmarket/users/login/kakao")
    public String login(@RequestParam("code") String code, HttpSession session) {
        System.out.println("\n===============================================\n");
        System.out.println("[login Controller] 로그인 로직 실행");
        System.out.println("[login Controller] 토큰 받기 요청에 필요한 인가 코드 : " + code);

        String access_token = kakaoAPI.getAccessToken(code);
        System.out.println("\n[login Controller] 사용자 액세스 토큰 값 : " + access_token);
        session.setAttribute("access_token", access_token);

        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_token);
        System.out.println("\n[login Controller] HashMap<String, Object> userInfo : " + userInfo);
        session.setAttribute("user_email", userInfo.get("email"));

        System.out.println("[login Controller] 로그인 성공!");
        return "index";
    }

    @RequestMapping(value = "/wowmarket/users/kakao/unlink")
    public String unlink(HttpSession session) {
        System.out.println("\n===============================================\n");
        System.out.println("[unlink Controller] 연결 끊기 로직 실행");

        kakaoAPI.unLink((String) session.getAttribute("access_token"));
        session.invalidate();

        System.out.println("\n[unlink Controller] 연결 끊기 성공! -> 다시 로그인을 원할 경우 localhost:8080으로 이동해 주세요!");
        return "index";
    }

    @RequestMapping(value = "/wowmarket/users/logout/kakao")
    public String logout(HttpSession session) {
        System.out.println("\n===============================================\n");
        System.out.println("[logout Controller] 로그아웃 로직 실행");

        kakaoAPI.logout((String) session.getAttribute("access_token"));
        session.removeAttribute("access_token");
        session.removeAttribute("user_email");

        System.out.println("\n[logout Controller] 로그아웃 성공!");
        return "index";
    }
}
