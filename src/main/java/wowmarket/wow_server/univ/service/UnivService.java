package wowmarket.wow_server.univ.service;

import com.univcert.api.UnivCert;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.univ.dto.UnivCodeRequestDto;
import wowmarket.wow_server.univ.dto.UnivRequestDto;

import java.io.*;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnivService {
    private final static boolean univ_check = true; //true : 대학 재학, false : 메일 소유
    private final EntityManager em;

    @Value("${api-key.univCert}")
    private String univCertAPIkey;

    public JSONObject univCertCertify(UnivRequestDto univRequestDto, User user) throws IOException {
        Optional.ofNullable(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 식별 정보 없음"));
        Map<String, Object> response = UnivCert.certify(univCertAPIkey, univRequestDto.getUniv_email(), univRequestDto.getUniv_name(), univ_check);
        return new JSONObject(response);
    }

    @Transactional
    public JSONObject univCertCertifyCode(UnivCodeRequestDto univCodeRequestDto, User user) throws IOException {
        Optional.ofNullable(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 식별 정보 없음"));

        Map<String, Object> response = UnivCert.certifyCode(univCertAPIkey,
                univCodeRequestDto.getUniv_email(), univCodeRequestDto.getUniv_name(), univCodeRequestDto.getCode());

        if ((boolean) response.get("success")) {
            em.createQuery("update User u set " +
                            "u.univ = :univName, u.univ_certified_date = :certified_date, u.univ_check = true " +
                            "where u.id = :id")
                    .setParameter("univName", response.get("univName").toString())
                    .setParameter("certified_date", response.get("certified_date").toString())
                    .setParameter("id", user.getId())
                    .executeUpdate();
        }

        return new JSONObject(response);
    }

    @Transactional
    public JSONObject univCertClear() throws IOException {
        Map<String, Object> response = UnivCert.clear(univCertAPIkey);

        //모든 유저의 학교 정보 초기화
        em.createQuery("update User u set u.univ = null, u.univ_certified_date = null, u.univ_check = false")
                .executeUpdate();

        return new JSONObject(response);
    }
}