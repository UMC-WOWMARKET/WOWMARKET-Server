package wowmarket.wow_server.mypage.myproject.MyDemandProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandProjectModifyRequestDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandResponseDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.service.MyDemandProjectService;

@RestController
@RequestMapping("/myproject/demand")
@RequiredArgsConstructor
public class MyDemandProjectController {

    private final MyDemandProjectService myDemandProjectService;

    @GetMapping
    public MyDemandResponseDto getMyDemandList(@RequestParam(value = "page", defaultValue = "1", required = false)int page, @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myDemandProjectService.findAllMyDemandForm(pageable, user);
    }

    @PutMapping("/{demand_project_id}")
    public ResponseEntity updateMyDemandFormStatus(@PathVariable Long demand_project_id){
        myDemandProjectService.updateMyDemandFormStatus(demand_project_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //수요조사 등록폼 상세보기
    @GetMapping("/detail/{demand_project_id}")
    public MyDemandDetailResponseDto getMyDemandDetailForm(@PathVariable Long demand_project_id, @AuthenticationPrincipal User user){
        return myDemandProjectService.findMyDemandFormDetail(demand_project_id, user);
    }


    @PutMapping("/{demand_project_id}/modify")
    public ResponseEntity modifyMyDemandProject(@PathVariable Long demand_project_id, @RequestBody MyDemandProjectModifyRequestDto requestDto, @AuthenticationPrincipal User user){
        return myDemandProjectService.modifyMyDemandProject(demand_project_id, requestDto, user);
    }

}
