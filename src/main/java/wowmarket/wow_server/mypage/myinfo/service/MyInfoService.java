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
    public MyInfoResponseDto findMyInfo(User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 정보 없음");
        MyInfoResponseDto responseDto = MyInfoResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .univ(user.getUniv())
                .build();
        return responseDto;
    }
}
