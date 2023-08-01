package wowmarket.wow_server.mypage.myproject.controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormListResponseDto;
import wowmarket.wow_server.mypage.myproject.service.MyProjectService;

@RestController
@RequestMapping("/wowmarket/mypage")
@RequiredArgsConstructor
public class MyProjectController {
    private final MyProjectService myProjectService;

    @GetMapping("/{user_id}/myproject")
    public MySalesFormListResponseDto getMySalesList(@PathVariable Long user_id, @RequestParam(value = "page", defaultValue = "1", required = false)int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myProjectService.findAllMySalesForm(user_id, pageable);
    }

    @PutMapping("/{user_id}/myproject/{project_id}")
    public ResponseEntity finishMySales(@PathVariable Long project_id){
        myProjectService.finishMySalesForm(project_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{user_id}/myproject/detail/{project_id}")
    public MySalesFormDetailResponseDto getMySalesDetail(@PathVariable Long user_id, @PathVariable Long project_id){
        return myProjectService.findMySalesDetail(project_id);
    }


}
