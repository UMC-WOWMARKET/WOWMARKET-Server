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
public class KakaoAPIService {
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
            System.out.println("\n[getAccessToken] 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[getAccessToken] response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("[getAccessToken] access_token : " + access_token);
            System.out.println("[getAccessToken] refresh_token : " + refresh_token);

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
            System.out.println("\n[getUserInfo] 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[getUserInfo] response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            System.out.println("[getUserInfo] nickname = " + nickname);
            System.out.println("[getUserInfo] email = " + email);

            //findByEmail로 값이 없으면 DB에 저장 userRepository.findByEmail(email).isEmpty()
            if (userRepository.findByEmail(email).isEmpty()) {
                System.out.println("\n[getUserInfo] nickname과 email 값 잘 넘어왔고 DB에 해당 email 없어서 DB에 저장하는 로직 실행");
                KakaoDto kakaoDto = new KakaoDto(email, nickname, Login_Method.KAKAO);
                User User = userRepository.save(kakaoDto.toEntity());
                System.out.println("[getUserInfo] DB 저장 후 User 테이블 userId 확인 userRepository.findByEmail(email).get().getId() : " + userRepository.findByEmail(email).get().getId());
            } else {
                System.out.println("\n[getUserInfo] DB에 해당 email 이미 저장되어 있어서 DB에 저장하는 로직 실행하지 않음");
            }

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public void unLink(String access_token) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("\n[unLink] 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[unLink] response body : " + result + " 연결 끊기에 성공한 사용자의 회원번호, 이 회원번호는 카카오의 회원번호일까? 근데 매번 바뀌는 거 같은디");

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(String acccess_token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + acccess_token);

            int responseCode = conn.getResponseCode();
            System.out.println("\n[logout] 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[logout] response body : " + result);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
