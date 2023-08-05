package wowmarket.wow_server.univ.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.univ.dto.UnivCodeRequestDto;
import wowmarket.wow_server.univ.dto.UnivRequestDto;
import wowmarket.wow_server.univ.dto.UnivResponseDto;
import wowmarket.wow_server.univ.dto.univCert.certifyCodeRequestDto;
import wowmarket.wow_server.univ.dto.univCert.certifyRequestDto;
import wowmarket.wow_server.repository.UserRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UnivService {
    private final static boolean univ_check = true; //true : 대학 재학, false : 메일 소유
    private final UserRepository userRepository;
    @Value("${api-key.univCert}")
    private String univCertAPIkey;

    public UnivResponseDto univCertCertify(UnivRequestDto univRequestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 식별 정보 없음"));

        String univCertifySuccess = "";
        String reqURL = "https://univcert.com/api/v1/certify";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json"); // JSON 데이터 보내기
            conn.setDoOutput(true);

            certifyRequestDto certifyRequestDto = new certifyRequestDto(univCertAPIkey,
                    univRequestDto.getUniv_name(), univRequestDto.getUniv_email(), univ_check);
            String univRequestJson = certifyRequestDto.toJson();
            System.out.println("[univCertCertify] univRequestJson : " + univRequestJson);

            // 요청 본문을 전송하기 위한 OutputStream을 얻어옴
            conn.setDoOutput(true);
            OutputStream outputStream = conn.getOutputStream();

            // JSON 데이터를 OutputStream을 통해 전송
            outputStream.write(univRequestJson.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            System.out.println("[univCertCertify] 상태코드 반환 : " + responseCode);

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
            System.out.println("[univCertCertify] response body : " + result);
            br.close();

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            univCertifySuccess = element.getAsJsonObject().get("success").getAsString();
            System.out.println("[univCertCertify] 인증번호 발송 성공 여부 univCertifySuccess : " + univCertifySuccess);
            if (element.getAsJsonObject().get("code").getAsString().equals("400") &&
                    element.getAsJsonObject().has("message") &&
                    element.getAsJsonObject().get("message").getAsString().equals("이미 완료된 요청입니다.")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이미 인증이 완료되었습니다!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return UnivResponseDto.builder()
                .success(univCertifySuccess)
                .build();
    }

    public UnivResponseDto univCertCertifyCode(UnivCodeRequestDto univCodeRequestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        String univCertifyCodeSuccess = "";
        String reqURL = "https://univcert.com/api/v1/certifycode";

        String loginUserEmail = SecurityUtil.getLoginUsername();

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json"); // JSON 데이터를 보내기
            conn.setDoOutput(true);

            certifyCodeRequestDto codeRequestDto = new certifyCodeRequestDto(univCertAPIkey,
                    univCodeRequestDto.getUniv_name(),
                    univCodeRequestDto.getUniv_email(),
                    univCodeRequestDto.getCode());
            String codeRequestJson = codeRequestDto.toJson();
            System.out.println("[univCertCertifyCode] codeRequestJson : " + codeRequestJson);

            // 요청 본문을 전송하기 위한 OutputStream을 얻어옴
            conn.setDoOutput(true);
            OutputStream outputStream = conn.getOutputStream();

            // JSON 데이터를 OutputStream을 통해 전송
            outputStream.write(codeRequestJson.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            System.out.println("[univCertCertifyCode] 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[univCertCertifyCode] response body : " + result);
            br.close();

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            univCertifyCodeSuccess = element.getAsJsonObject().get("success").getAsString();
            System.out.println("[univCertCertifyCode] 인증번호 일치 성공 여부 univCertifyCodeSuccess : " + univCertifyCodeSuccess);
            if (element.getAsJsonObject().get("code").getAsString().equals("400") &&
                    element.getAsJsonObject().has("message") &&
                    element.getAsJsonObject().get("message").getAsString().equals("일치하지 않는 인증코드입니다.")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "일치하지 않는 인증코드입니다.");
            }

            //성공이면 유저 학교 관련 정보 업데이트 로직 추가
            if (univCertifyCodeSuccess.equals("true")) {
                String certified_date = element.getAsJsonObject().get("certified_date").getAsString();
                LocalDateTime user_univ_auth = LocalDateTime.parse(certified_date);
                user.updateUniv(element.getAsJsonObject().get("univName").getAsString(),
                        user_univ_auth, true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return UnivResponseDto.builder()
                .success(univCertifyCodeSuccess)
                .build();
    }

    public String univCertClear() {
        String univClearSuccess = "";
        String reqURL = "https://univcert.com/api/v1/clear";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json"); // JSON 데이터를 보내기
            conn.setDoOutput(true);

            String clearRequestJson = "{\"key\":\"" + univCertAPIkey + "\"}";
            System.out.println("[univCertClear] codeRequestJson : " + clearRequestJson);

            // 요청 본문을 전송하기 위한 OutputStream을 얻어옴
            conn.setDoOutput(true);
            OutputStream outputStream = conn.getOutputStream();

            // JSON 데이터를 OutputStream을 통해 전송
            outputStream.write(clearRequestJson.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            System.out.println("[univCertClear] 상태코드 반환 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[univCertClear] response body : " + result);
            br.close();

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            univClearSuccess = element.getAsJsonObject().get("success").getAsString();
            System.out.println("[univCertClear] 인증 유저 초기화 성공 여부 univClearSuccess : " + univClearSuccess);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return univClearSuccess;
    }
}
