package wowmarket.wow_server.login.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wowmarket.wow_server.login.service.KakaoAPI;

import java.util.HashMap;

@Controller
public class HomeController {
    @Autowired
    KakaoAPI kakaoAPI;

    @RequestMapping(value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(value="/wowmarket/users/login/kakao")
    public String login(@RequestParam("code") String code, HttpSession session) {
        System.out.println("\n" + " =========================== ");
        System.out.println("토큰 받기 요청에 필요한 인가 코드 : " + code +  "\n");

        String access_token = kakaoAPI.getAccessToken(code);
        System.out.println("사용자 액세스 토큰 값 : " + access_token + "\n");

        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_token);
        System.out.println("login Controller : " + userInfo);

        //클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            System.out.println("세션 userId : " + session.getAttribute("userId"));
            session.setAttribute("access_token", access_token);
        }

        return "index";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakaoAPI.kakaoLogout((String) session.getAttribute("access_token"));
        session.removeAttribute("access_token");
        session.removeAttribute("userId");

        return "index";
    }
}