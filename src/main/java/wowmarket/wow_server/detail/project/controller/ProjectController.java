package wowmarket.wow_server.detail.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.detail.project.dto.ProjectInfoResponseDto;
import wowmarket.wow_server.detail.project.service.ProjectService;
import wowmarket.wow_server.detail.project.dto.ProjectImgResponseDto;
import wowmarket.wow_server.domain.Project;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    //프로젝트: 상세 정보 조회
    @GetMapping("/{project_id}")
    public ProjectInfoResponseDto getProjectInfo(@PathVariable Long project_id) {
        return projectService.getProjectInfo(project_id);
    }

    //프로젝트: 굿즈 소개(이미지 3개) 조회
    @GetMapping("/{project_id}/img")
    public ProjectImgResponseDto getProjectImg(@PathVariable Long project_id) {
        return projectService.getProjectImg(project_id);
    }

}