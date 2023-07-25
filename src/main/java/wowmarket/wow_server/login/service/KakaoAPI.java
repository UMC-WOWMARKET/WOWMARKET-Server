package wowmarket.wow_server.login.service;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Login_Method;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.login.dto.KakaoDto;
import wowmarket.wow_server.repository.UserRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * 인증 코드로 Access_Token을 받아올 작업을 수행할 Service 클래스 생성
 * 및 getAccessToken메소드 작성
 */
@Service
@RequiredArgsConstructor
public class KakaoAPI {
    private final UserRepository userRepository;

    public String getAccessToken(String authorize_code) {
        String access_token = "";
        String refresh_token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=394cbd2e5e0ad400adbc202784ad624b");
            sb.append("&redirect_uri=http://localhost:8080/wowmarket/users/login/kakao");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("getAccessToken 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("getAccessToken response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_token);
            System.out.println("refresh_token : " + refresh_token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_token;
    }

    @Transactional
    public HashMap<String, Object> getUserInfo(String access_token) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("getUserInfo 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            System.out.println(" \n\n element는 뭘까.. element = " + element);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            System.out.println("\n JsonObject로 넘어오는 값은 뭘까 kakao_account = " + kakao_account);
            System.out.println("\n 그러면 properties는 뭘까 properties = " + properties);
            System.out.println(" \n 이메일 동의 했는지 확인 kakao_account.getAsJsonObject().get(\"has_email\").getAsString() : " + kakao_account.getAsJsonObject().get("has_email").getAsString() + "\n\n");

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            //has_email로 이메일 동의 했는지 확인
            //동의 안 했으면 빈 해쉬값이 넘어가서 controller에서 연결 끊기 api가 실행되고 다시 로그인 하도록 설정
            if (kakao_account.getAsJsonObject().get("has_email").getAsString() == "false") {
                return userInfo;
            }
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            System.out.println("getUserInfo nickname = " + nickname);
            System.out.println("getUserInfo email = " + email);
//
//            System.out.println("\n\n **** userRepository.findByEmail(email).isEmpty() : " + userRepository.findByEmail(email).isEmpty());
//            System.out.println("userRepository.findByEmail(email) : " + userRepository.findByEmail(email) + "\n\n");

            //findByEmail로 값이 없으면 DB에 저장
            if (userRepository.findByEmail(email).isEmpty()) {
                KakaoDto kakaoDto = new KakaoDto(email, nickname, Login_Method.KAKAO);
                User User = userRepository.save(kakaoDto.toEntity());
            }

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public void kakaoLogout(String acccess_token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + acccess_token);

            int responseCode = conn.getResponseCode();
            System.out.println("로그아웃 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("로그아웃 response body : " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
