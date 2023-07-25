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
        System.out.println("\n" + " =========================== ");
        System.out.println("토큰 받기 요청에 필요한 인가 코드 : " + code +  "\n");

        String access_token = kakaoAPI.getAccessToken(code);
        System.out.println("사용자 액세스 토큰 값 : " + access_token + "\n");

        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_token);
        System.out.println("\n\n HashMap<String, Object>는 뭐가 나올까 userInfo = " + userInfo);
        if (!userInfo.containsKey("email")) { //값이 없으면 false인데 !로 true로 바꿔서 이메일 없을 경우 로직 실행
            System.out.println("\n\n 이메일 값이 넘어오지 않아서 다시 로그인 페이지 index() 호출 \n\n");
            check_email(code);
        }
        System.out.println("login Controller : " + userInfo);

        //클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            System.out.println("세션 userId : " + session.getAttribute("userId"));
            session.setAttribute("access_token", access_token);
        }

        return "index";
    }

    @PostMapping("/wowmarket/users/login/kakao/check_email")
    @ResponseBody // JSON 형태로 응답 반환을 위해 @ResponseBody 어노테이션 사용
    public ResponseEntity<Map<String, String>> check_email(String code) {
        System.out.println("\n\n 이메일 제공 미동의로 인한 로직 check_email");
        String access_token = kakaoAPI.getAccessToken(code);

        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_token);

        Map<String, String> response = new HashMap<>();
        if (userInfo.containsKey("email")) {
            response.put("email", userInfo.get("email").toString());
        } else {
            response.put("email", "");
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakaoAPI.kakaoLogout((String) session.getAttribute("access_token"));
        session.removeAttribute("access_token");
        session.removeAttribute("userId");

        return "index";
    }
}
