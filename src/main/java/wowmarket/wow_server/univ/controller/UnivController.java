package wowmarket.wow_server.univ.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.univ.dto.UnivCodeRequestDto;
import wowmarket.wow_server.univ.dto.UnivRequestDto;
import wowmarket.wow_server.univ.service.UnivService;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class UnivController {
    private final UnivService univService;

    @PostMapping("/wowmarket/users/univCert")
    public JSONObject univCertify(@RequestBody UnivRequestDto univRequestDto,
                                  @AuthenticationPrincipal User user) throws IOException {
        System.out.println("\n[univCertify Controller] 학교 인증 시작\n");
        return univService.univCertCertify(univRequestDto, user);
    }

    @PostMapping("/wowmarket/users/univCert/code")
    public JSONObject univCertifyCode(@RequestBody UnivCodeRequestDto univCodeRequestDto,
                                           @AuthenticationPrincipal User user) throws IOException {
        System.out.println("\n[univCertify Controller] 인증 코드로 학교 인증\n");
        return univService.univCertCertifyCode(univCodeRequestDto, user);
    }

    @PostMapping("wowmarket/users/univCert/clear")
    public JSONObject univClear() throws IOException {
        System.out.println("\n[univCertify Controller] 인증 유저 목록 초기화\n");
        return univService.univCertClear();
    }

}
