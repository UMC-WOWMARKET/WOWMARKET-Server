package wowmarket.wow_server.mypage.myproject.MyDemandProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandResponseDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.service.MyDemandProjectService;

@RestController
@RequestMapping("/wowmarket/mypage/myproject/demand")
@RequiredArgsConstructor
public class MyDemandProjectController {

    private final MyDemandProjectService myDemandProjectService;

    @GetMapping
    public MyDemandResponseDto getMyDemandList(@RequestParam(value = "page", defaultValue = "1", required = false)int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myDemandProjectService.findAllMyDemandForm(pageable);
    }

    @PutMapping("/{demand_project_id}")
    public ResponseEntity updateMyDemandFormStatus(@PathVariable Long demand_project_id){
        myDemandProjectService.updateMyDemandFormStatus(demand_project_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/detail/{demand_project_id}")
    public MyDemandDetailResponseDto getMyDemandDetailForm(@PathVariable Long demand_project_id){
        return myDemandProjectService.findMyDemandFormDetail(demand_project_id);
    }



}
