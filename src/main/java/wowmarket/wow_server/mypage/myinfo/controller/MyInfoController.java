package wowmarket.wow_server.mypage.myinfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myinfo.dto.GetLikeProjectDto;
import wowmarket.wow_server.mypage.myinfo.dto.MyInfoResponseDto;
import wowmarket.wow_server.mypage.myinfo.service.MyInfoService;

@RestController
@RequestMapping("/mypage/myinfo")
@RequiredArgsConstructor
public class MyInfoController {
    private final MyInfoService myInfoService;

    @GetMapping()
    public MyInfoResponseDto getMyInfo(@AuthenticationPrincipal User user) {
        return myInfoService.findMyInfo(user);
    }

    @GetMapping("/like/project")
    public GetLikeProjectDto getLikeProjects(
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @AuthenticationPrincipal User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_time");
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return myInfoService.getLikeProjects(user, pageable);
    }

    @GetMapping("/like/demandProject")
    public GetLikeProjectDto getLikeDemandProjects(
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @AuthenticationPrincipal User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_time");
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return myInfoService.getLikeDemandProjects(user, pageable);
    }
}
