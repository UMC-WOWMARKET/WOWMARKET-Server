package wowmarket.wow_server.detail.demandproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.detail.demandproject.dto.DemandItemResponseDto;
import wowmarket.wow_server.detail.demandproject.dto.DemandProjectImgResponseDto;
import wowmarket.wow_server.detail.demandproject.dto.DemandProjectInfoResponseDto;
import wowmarket.wow_server.detail.demandproject.service.DemandItemService;
import wowmarket.wow_server.detail.demandproject.service.DemandProjectService;

import java.util.List;

@RestController
@RequestMapping("/demand_project")
@RequiredArgsConstructor
public class DemandProjectController {
    private final DemandProjectService demandProjectService;
    private final DemandItemService demandItemService;


    //참여폼: 상세 정보 조회
    @GetMapping("/{demand_project_id}")
    public DemandProjectInfoResponseDto getProjectInfo(@PathVariable Long demand_project_id) {
        return demandProjectService.getDemandProjectInfo(demand_project_id);
    }

    //참여폼: 굿즈 소개(이미지 3개) 조회
    @GetMapping("/{demand_project_id}/img")
    public DemandProjectImgResponseDto getProjectImg(@PathVariable Long demand_project_id) {
        return demandProjectService.getDemandProjectImg(demand_project_id);
    }

    //참여폼: 우측 폼 정보 불러오기(상품명, 판매가)
    @GetMapping("/{demand_project_id}/item") //path 수정해야함
    public List<DemandItemResponseDto> getItemInfo(@PathVariable Long demand_project_id){
        return demandItemService.getDemandItemInfo(demand_project_id);
    }

}