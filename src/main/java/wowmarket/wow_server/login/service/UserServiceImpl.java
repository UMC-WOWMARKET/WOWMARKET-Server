package wowmarket.wow_server.login.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.JwtTokenProvider;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.login.dto.TokenResponseDto;

import wowmarket.wow_server.login.dto.UserSignInRequestDto;
import wowmarket.wow_server.login.dto.UserSignUpRequestDto;
import wowmarket.wow_server.repository.UserRepository;


import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender mailSender;
    private final RedisTemplate redisTemplate;

    @Override
    public Long signUp(UserSignUpRequestDto requestDto) throws Exception {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //이메일 중복 시 400 에러 반환
        }

        User User = userRepository.save(requestDto.toEntity());
        User.encodePassword(passwordEncoder); // 비밀번호 암호화

        return User.getId();
    }

    @Override
    public TokenResponseDto signIn(UserSignInRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        user.updateRefreshToken(refreshToken); //refreshToken DB에 저장
        userRepository.save(user);

        return TokenResponseDto.builder()
                .temporaryPw(user.isTemporary_password()) //임시비밀번호인지 전달
                .grantType("Bearer")
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }

    @Override
    public void sendMailAndChangePassword(String email) {
        //비밀번호 재설정
        String str = getTempPassword();
        updatePassword(email, str, true);

        // 메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("admin@wowmarket.com"); //이걸로 했는데 왜 안보내지냐
        message.setSubject("와우상점 임시 비밀번호 안내입니다.");
        message.setText("안녕하세요. 와우상점 임시 비밀번호 안내 관련 이메일 입니다. 회원님의 임시 비밀번호는 "
                + str + " 입니다. 로그인 후에 비밀번호를 변경을 해주세요.");

        mailSender.send(message);
    }

    @Override
    public Long updatePassword(String email, String str, Boolean temp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        user.updatePassword(passwordEncoder.encode(str),temp);
        return user.getId();
    }

    @Override
    public String getTempPassword() {
        String chars = "ABCDEFCHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom rm = new SecureRandom();

        return IntStream.range(0, 10)
                .map(i -> rm.nextInt(chars.length()))
                .mapToObj(rmIndex -> String.valueOf(chars.charAt(rmIndex)))
                .collect(Collectors.joining());
    }

    public ResponseEntity logout(HttpServletRequest request){
        String token = jwtTokenProvider.resolveAccessToken(request);

        //aceess token으로 가져온 유저의 refresh token 삭제
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        user.updateRefreshToken(null);
        userRepository.save(user);

        // access token의 유효시간 가져와서 블랙리스트 등록
        Long expiration = jwtTokenProvider.getExpiration(token);
        redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok().build();
    }

    @Override
    public TokenResponseDto issueAccessToken(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if(!jwtTokenProvider.validateAccessToken(accessToken)){
            log.info("access token 만료");
            if(jwtTokenProvider.validateRefreshToken(refreshToken)){
                log.info("refresh token 유효");

                User user = userRepository.findByEmail(jwtTokenProvider.getUserEmail(refreshToken))
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

                // refresh token의 값이 같다면
                if(refreshToken.equals(user.getRefreshToken())){
                    accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
                } else {
                    log.info("refresh token 변조");
                }
            } else {
                log.info("refresh token 유효하지 않음");
            }
        }
        return TokenResponseDto.builder()
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }
}
