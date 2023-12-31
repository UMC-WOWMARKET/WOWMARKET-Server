package wowmarket.wow_server.login.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Login_Method;
import wowmarket.wow_server.domain.Role;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.JwtTokenProvider;
import wowmarket.wow_server.login.dto.KakaoResponseDto;
import wowmarket.wow_server.repository.UserRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class KakaoAPIService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

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
            sb.append("&client_id=" + client_id);
            sb.append("&redirect_uri=" + redirect_uri);
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("[getAccessToken] 상태코드 반환 : " + responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) { // 정상적인 응답인 경우
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else { // 에러 응답인 경우
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
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

            System.out.println("[getAccessToken] 카카오 access_token : " + access_token);
            System.out.println("[getAccessToken] 카카오 refresh_token : " + refresh_token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_token;
    }

    @Transactional
    public KakaoResponseDto getUserInfo(String access_token) {
        String univ = "";
        String jwtAccessToken = "";
        String jwtRefreshToken = "";
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("[getUserInfo] 상태코드 반환 : " + responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) { // 정상적인 응답인 경우
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else { // 에러 응답인 경우
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
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
            if (userRepository.findByEmail(email).isEmpty()) { // 회원가입
                System.out.println("[getUserInfo] 회원가입");

                User user = User.builder()
                        .name(nickname)
                        .email(email)
                        .login_method(Login_Method.KAKAO)
                        .role(Role.ROLE_USER)
                        .build();
                userRepository.save(user);

                jwtAccessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole().name());
                jwtRefreshToken = jwtTokenProvider.createRefreshToken();
                user.updateRefreshToken(jwtRefreshToken); //refreshToken DB에 저장

            } else { // 로그인
                System.out.println("[getUserInfo] 로그인");

                User user = userRepository.findByEmail(email).get();
                univ = user.getUniv();

                jwtAccessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole().name());
                jwtRefreshToken = jwtTokenProvider.createRefreshToken();
                user.updateRefreshToken(jwtRefreshToken); //jwtRrefreshToken DB에 저장
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return KakaoResponseDto.builder()
                .temporaryPw(false)
                .grantType("Bearer")
                .jwtAccessToken(jwtAccessToken)
                .jwtRefreshToken(jwtRefreshToken)
                .univ(univ)
                .build();
    }

    public void unLink(String access_token) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("[unLink] 상태코드 반환 : " + responseCode);

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
            System.out.println("[logout] 상태코드 반환 : " + responseCode);

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
