package wowmarket.wow_server.mypage.myinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myinfo.dto.MyInfoResponseDto;
import wowmarket.wow_server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyInfoResponseDto findMyInfo(){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        MyInfoResponseDto responseDto = MyInfoResponseDto.builder()
                .userid(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return responseDto;
    }
}
