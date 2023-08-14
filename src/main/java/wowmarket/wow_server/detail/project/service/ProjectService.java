package wowmarket.wow_server.detail.project.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.notice.dto.NoticeResponseDto;
import wowmarket.wow_server.detail.project.dto.ProjectImgResponseDto;
import wowmarket.wow_server.detail.project.dto.ProjectInfoResponseDto;
import wowmarket.wow_server.domain.Notice;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.NoticeRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.List;

@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    //프로젝트: 굿즈 소개(이미지 3개) 조회
    public ProjectImgResponseDto getProjectImg(Long project_id){
        Project project = projectRepository.findByProject_Id(project_id);
        return new ProjectImgResponseDto(project);
    }

    //프로젝트: 상세 정보 조회
    public ProjectInfoResponseDto getProjectInfo(Long project_id){
        Project project = projectRepository.findByProject_Id(project_id);
        projectRepository.updateView(project_id); //상세정보 조회할 때마다 조회수 +1
        return new ProjectInfoResponseDto(project);
    }
}