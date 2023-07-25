package wowmarket.wow_server.login.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.JwtTokenProvider;
import wowmarket.wow_server.login.dto.TokenResponseDto;
import wowmarket.wow_server.login.dto.UserSignInRequestDto;
import wowmarket.wow_server.login.dto.UserSignUpRequestDto;
import wowmarket.wow_server.repository.UserRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public Long signUp(UserSignUpRequestDto requestDto) throws Exception {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //이메일 중복 시 400 에러 반환
        }

        User User = userRepository.save(requestDto.toEntity());
        User.encodePassword(passwordEncoder);

        return User.getId();
    }

    @Override
    @Transactional
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
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
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
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
