package wowmarket.wow_server.login.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wowmarket.wow_server.login.dto.UnivCertifyCodeRequestDto;
import wowmarket.wow_server.login.dto.UnivCertifyRequestDto;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class UnivService {
    private final static String univCertAPI = "e8ad2247-491c-4e61-b6aa-46b3776fe7e6";
    private final static boolean univ_check = true; //true : 대학 재학, false : 메일 소유

    public String univCertCertify(String univ_name, String univ_email) {
        String univCertifySuccess = "";
        String reqURL = "https://univcert.com/api/v1/certify";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json"); // JSON 데이터 보내기
            conn.setDoOutput(true);

            UnivCertifyRequestDto univCertifyRequestDto = new UnivCertifyRequestDto(univCertAPI, univ_name, univ_email, univ_check);
            String univRequestJson = univCertifyRequestDto.toJson();
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

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return univCertifySuccess;
    }

    public String univCertCertifyCode(String univ_name, String univ_email, int code) {
        String univCertifyCodeSuccess = "";
        String reqURL = "https://univcert.com/api/v1/certifycode";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json"); // JSON 데이터를 보내기
            conn.setDoOutput(true);

            UnivCertifyCodeRequestDto codeRequestDto = new UnivCertifyCodeRequestDto(univCertAPI, univ_name, univ_email, code);
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return univCertifyCodeSuccess;
    }
}
