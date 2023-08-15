package wowmarket.wow_server.login.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.JwtTokenProvider;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.login.dto.TokenResponseDto;
import wowmarket.wow_server.login.dto.UserSignInRequestDto;
import wowmarket.wow_server.login.dto.UserSignUpRequestDto;
import wowmarket.wow_server.login.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

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
    public ResponseEntity sendTempPw(String email){
        userService.sendMailAndChangePassword(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/resetPw")
    public ResponseEntity resetPw(@Valid @RequestBody UserSignInRequestDto request){
        userService.updatePassword(request.getEmail(), request.getPassword(), false);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping ("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(HttpServletRequest request, @AuthenticationPrincipal User user){
        String token = jwtTokenProvider.resolveAccessToken(request);
        userService.logout(token, user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/newAccess")
    public ResponseEntity issueAccessToken(HttpServletRequest request){
        return new ResponseEntity(userService.issueAccessToken(request), HttpStatus.OK);
    }
}
