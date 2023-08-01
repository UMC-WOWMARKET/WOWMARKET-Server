package wowmarket.wow_server.login.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.login.service.UnivService;


@RestController
@RequiredArgsConstructor
public class UnivController {
    private final UnivService univService;

    @PostMapping("/wowmarket/users/univCert")
    public String univCertify(@RequestParam String univ_name, @RequestParam String univ_email) {
        System.out.println("\n===============================================\n");
        System.out.println("[univCertify Controller] 학교 인증 시작\n");
        return univService.univCertCertify(univ_name, univ_email);
    }

    @PostMapping("/wowmarket/users/univCert/code")
    public String univCertifyCode(@RequestParam String univ_name,
                                  @RequestParam String univ_email,
                                  @RequestParam int code) {
        System.out.println("\n[univCertify Controller] 인증 코드로 학교 인증\n");
        return univService.univCertCertifyCode(univ_name, univ_email, code);
    }

}
