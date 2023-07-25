package wowmarket.wow_server.mypage.myinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myinfo.dto.MyInfoResponseDto;
import wowmarket.wow_server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyInfoResponseDto findMyInfo(Long userid){
        User user = userRepository.findById(userid).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        MyInfoResponseDto responseDto = MyInfoResponseDto.builder()
                .userid(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return responseDto;
    }
}
