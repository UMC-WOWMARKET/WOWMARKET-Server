package wowmarket.wow_server.detail.project.service;

import org.springframework.stereotype.Service;
import wowmarket.wow_server.detail.project.dto.ProjectImgResponseDto;
import wowmarket.wow_server.detail.project.dto.ProjectInfoResponseDto;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.itemRepository = itemRepository;
    }

    //주문폼: 굿즈 소개(이미지 3개) 조회
    public ProjectImgResponseDto getProjectImg(Long project_id){
        Project project = projectRepository.findByProject_Id(project_id);
        return new ProjectImgResponseDto(project);
    }

    //주문폼: 상세 정보 조회
    public ProjectInfoResponseDto getProjectInfo(Long project_id){
        Project project = projectRepository.findByProject_Id(project_id);
        projectRepository.updateView(project_id); //상세정보 조회할 때마다 조회수 +1
        return new ProjectInfoResponseDto(project, itemRepository.getTotalOrderCountByProject(project),
                itemRepository.getTotalGoalByProject(project));
    }
}