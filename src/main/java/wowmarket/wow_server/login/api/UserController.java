package wowmarket.wow_server.login.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.login.dto.TokenResponseDto;
import wowmarket.wow_server.login.dto.UserSignInRequestDto;
import wowmarket.wow_server.login.dto.UserSignUpRequestDto;
import wowmarket.wow_server.login.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/wowmarket/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity join (@Valid @RequestBody UserSignUpRequestDto request) throws Exception {
        userService.signUp(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@Valid @RequestBody UserSignInRequestDto request) throws Exception {
        return new ResponseEntity<>(userService.signIn(request), HttpStatus.OK);
    }

    @PostMapping("/sendTempPw")
    @ResponseStatus(HttpStatus.OK)
    public void sendTempPw(String email){
        userService.sendMailAndChangePassword(email);
    }

    @PostMapping("/resetPw")
    public ResponseEntity resetPw(@Valid @RequestBody UserSignInRequestDto request){
        userService.updatePassword(request.getEmail(), request.getPassword(), false);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping ("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(HttpServletRequest request){
       return userService.logout(request);
    }

    @PutMapping("/newAccess")
    public TokenResponseDto issueAccessToken(HttpServletRequest request){
        return userService.issueAccessToken(request);
    }
}
