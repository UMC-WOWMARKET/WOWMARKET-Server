package wowmarket.wow_server.login.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.login.dto.TokenResponseDto;
import wowmarket.wow_server.login.dto.UserSignInRequestDto;
import wowmarket.wow_server.login.dto.UserSignUpRequestDto;
import wowmarket.wow_server.login.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join (@Valid @RequestBody UserSignUpRequestDto request) throws Exception {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto login(@Valid @RequestBody UserSignInRequestDto request) throws Exception {
        return userService.signIn(request);
    }

    @PutMapping("/newAccess")
    public TokenResponseDto issueAccessToken(HttpServletRequest request){
        return userService.issueAccessToken(request);
    }
}
