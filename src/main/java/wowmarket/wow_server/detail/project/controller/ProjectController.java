package wowmarket.wow_server.detail.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.detail.project.dto.ItemResponseDto;
import wowmarket.wow_server.detail.project.dto.ProjectInfoResponseDto;
import wowmarket.wow_server.detail.project.service.ItemService;
import wowmarket.wow_server.detail.project.service.ProjectService;
import wowmarket.wow_server.detail.project.dto.ProjectImgResponseDto;
import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ItemService itemService;

    //주문폼: 상세 정보 조회
    @GetMapping("/{project_id}")
    public ProjectInfoResponseDto getProjectInfo(@PathVariable Long project_id) {
        return projectService.getProjectInfo(project_id);
    }

    //주문폼: 굿즈 소개(이미지 3개) 조회
    @GetMapping("/{project_id}/img")
    public ProjectImgResponseDto getProjectImg(@PathVariable Long project_id) {
        return projectService.getProjectImg(project_id);
    }


    //주문폼: 우측 폼 정보 불러오기(상품명, 판매가)
    @GetMapping("/{project_id}/item") //path 수정해야함
    public List<ItemResponseDto> getItemInfo(@PathVariable Long project_id){
        return itemService.getItemInfo(project_id);
    }

}