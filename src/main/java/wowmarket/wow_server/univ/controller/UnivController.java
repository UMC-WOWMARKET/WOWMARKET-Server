package wowmarket.wow_server.univ.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.univ.dto.UnivCodeRequestDto;
import wowmarket.wow_server.univ.dto.UnivRequestDto;
import wowmarket.wow_server.univ.dto.UnivResponseDto;
import wowmarket.wow_server.univ.service.UnivService;


@RestController
@RequiredArgsConstructor
public class UnivController {
    private final UnivService univService;

    @PostMapping("/wowmarket/users/univCert")
    @ResponseStatus(HttpStatus.OK)
    public UnivResponseDto univCertify(@Valid @RequestBody UnivRequestDto univRequestDto) {
        System.out.println("\n[univCertify Controller] 학교 인증 시작\n");
        return ResponseEntity.ok().body(univService.univCertCertify(univRequestDto)).getBody();
    }

    @PostMapping("/wowmarket/users/univCert/code")
    @ResponseStatus(HttpStatus.OK)
    public UnivResponseDto univCertifyCode(@Valid @RequestBody UnivCodeRequestDto univCodeRequestDto) {
        System.out.println("\n[univCertify Controller] 인증 코드로 학교 인증\n");
        return ResponseEntity.ok().body(univService.univCertCertifyCode(univCodeRequestDto)).getBody();
    }

    @PostMapping("wowmarket/users/univCert/clear")
    public String univClear() {
        System.out.println("\n[univCertify Controller] 인증 유저 목록 초기화\n");
        return univService.univCertClear();
    }

}
