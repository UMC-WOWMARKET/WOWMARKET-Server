package wowmarket.wow_server.mypage.myproject.MySalesProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myproject.MySalesProject.dto.MySalesDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.MySalesProject.dto.MySalesListResponseDto;
import wowmarket.wow_server.mypage.myproject.MySalesProject.service.MySalesProjectService;

@RestController
@RequestMapping("/myproject/sales")
@RequiredArgsConstructor
public class MySalesProjectController {
    private final MySalesProjectService mySalesProjectService;

    //판매 등록폼 전체보기
    @GetMapping()
    public MySalesListResponseDto getMySalesList(@RequestParam(value = "page", defaultValue = "1", required = false)int page, @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return mySalesProjectService.findAllMySalesForm(pageable, user);
    }

    //판매 등록폼 종료
    @PutMapping("/{project_id}")
    public ResponseEntity finishMySales(@PathVariable Long project_id, @AuthenticationPrincipal User user){
        return mySalesProjectService.finishMySalesForm(project_id, user);
    }

    //판매 등록폼 상세보기
    @GetMapping("/detail/{project_id}")
    public MySalesDetailResponseDto getMySalesDetail(@PathVariable Long project_id){
        return mySalesProjectService.findMySalesDetail(project_id);
    }

}
