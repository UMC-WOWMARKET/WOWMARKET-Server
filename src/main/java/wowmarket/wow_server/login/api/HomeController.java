package wowmarket.wow_server.login.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.login.service.KakaoAPI;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println("\n\n===============================================");
        System.out.println("[login Controller] 토큰 받기 요청에 필요한 인가 코드 : " + code);

        String access_token = kakaoAPI.getAccessToken(code);
        System.out.println("\n[login Controller] 사용자 액세스 토큰 값 : " + access_token);
        session.setAttribute("access_token", access_token);
        System.out.println("[login Controller] 사용자 액세스 토큰 값 세션에 저장 확인 session.getAttribute(\"access_token\") : " + session.getAttribute("access_token"));

        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_token);
        System.out.println("\n[login Controller] HashMap<String, Object> userInfo : " + userInfo);
        System.out.println("[login Controller] userInfo.containsKey(\"email\") : " + userInfo.containsKey("email"));
        if (!userInfo.containsKey("email")) { //값이 없으면 false인데 !로 true로 바꿔서 이메일 없을 경우 로직 실행
            System.out.println("[login Controller] 이메일 값이 넘어오지 않음!");
            check_email(access_token);
            System.out.println("\n[login Controller] check_email 호출");
        }
        System.out.println("[login Controller] login Controller userInfo : " + userInfo);

        //클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            System.out.println("[login Controller] 세션 userId 저장 : " + session.getAttribute("userId"));
//            session.setAttribute("access_token", access_token);
        }

        System.out.println("[login Controller] 로그인 성공!");
        return "index";
    }

    @PostMapping("/wowmarket/users/login/kakao/check_email")
    @ResponseBody // JSON 형태로 응답 반환을 위해 @ResponseBody 어노테이션 사용
    public ResponseEntity<Map<String, String>> check_email(String access_token) {
        System.out.println("\n[check_email Controller] 이메일 제공 미동의로 인한 로직 check_email 실행");

        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_token);

        Map<String, String> response = new HashMap<>();
        if (userInfo.containsKey("email")) {
            response.put("email", userInfo.get("email").toString());
        } else {
            response.put("email", "");
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/wowmarket/users/kakao/unlink")
    public String unlink(HttpSession session) {
        System.out.println("\n[unlink Controller] 연결 끊기 로직 실행");
        System.out.println("[unlink Controller] login Controller에서 세션에 access_token 넣었는데 토큰이 들어있는지 확인 session.getAttribute(\"access_token\") : " + session.getAttribute("access_token"));
        kakaoAPI.unLink((String) session.getAttribute("access_token"));
        session.invalidate();
        System.out.println("\n[unlink Controller] 연결 끊기 성공!");
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
