package wowmarket.wow_server.mypage.myinfo.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.mypage.myinfo.dto.MyInfoResponseDto;
import wowmarket.wow_server.mypage.myinfo.service.MyInfoService;

@RestController
@RequestMapping("wowmarket/mypage")
@RequiredArgsConstructor
public class MyInfoController {
    private final MyInfoService myInfoService;

    @GetMapping("/{user_id}")
    public MyInfoResponseDto getMyInfo(@PathVariable Long user_id){
        return myInfoService.findMyInfo(user_id);
    }

}
