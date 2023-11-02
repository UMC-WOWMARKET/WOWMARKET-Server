package wowmarket.wow_server.mypage.myinfo.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myinfo.dto.MyInfoResponseDto;
import wowmarket.wow_server.mypage.myinfo.service.MyInfoService;

@RestController
@RequestMapping("/mypage/myinfo")
@RequiredArgsConstructor
public class MyInfoController {
    private final MyInfoService myInfoService;

    @GetMapping()
    public MyInfoResponseDto getMyInfo(@AuthenticationPrincipal User user){
        System.out.println("deploy test");
        return myInfoService.findMyInfo(user);
    }

}
