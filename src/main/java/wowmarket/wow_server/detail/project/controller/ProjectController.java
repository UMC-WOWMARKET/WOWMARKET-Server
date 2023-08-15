package wowmarket.wow_server.detail.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.detail.demandproject.dto.DemandDetailRequestDto;
import wowmarket.wow_server.detail.project.dto.*;
import wowmarket.wow_server.detail.project.service.ItemService;
import wowmarket.wow_server.detail.project.service.OrderService;
import wowmarket.wow_server.detail.project.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ItemService itemService;
    private final OrderService orderService;

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


//    //주문폼: 우측 폼 정보 불러오기(상품명, 판매가)
//    @GetMapping("/{project_id}/item") //path 수정해야함
//    public List<ItemResponseDto> getItemInfo(@PathVariable Long project_id){
//        return itemService.getItemInfo(project_id);
//    }

    //주문폼: 우측 폼 정보 불러오기
    @GetMapping("/{project_id}/item") //path 수정해야함
    public OrderResponseDto getItemInfo(@PathVariable Long project_id){
        return itemService.getItemInfo(project_id);
    }

    //주문폼: 폼 등록
    @PostMapping("/{project_id}")
    public OrderFormRequestDto createDemandForm(@PathVariable Long project_id, @RequestBody OrderFormRequestDto requestDto){
        return orderService.createOrderForm(project_id, requestDto);
    }

}