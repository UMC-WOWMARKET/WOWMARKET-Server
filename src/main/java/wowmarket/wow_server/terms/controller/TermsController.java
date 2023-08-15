package wowmarket.wow_server.terms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class TermsController {

    @GetMapping("/privacy-policy")
    public ResponseEntity getPrivacyPolicy(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/terms-condition")
    public ResponseEntity getTermsCondition(){
        return new ResponseEntity(HttpStatus.OK);
    }

}
