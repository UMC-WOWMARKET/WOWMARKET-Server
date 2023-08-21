package wowmarket.wow_server.detail.demandproject.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandProjectImgResponseDto;
import wowmarket.wow_server.detail.demandproject.dto.DemandProjectInfoResponseDto;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.DemandItemRepository;
import wowmarket.wow_server.repository.DemandProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.List;

@Service
public class DemandProjectService {
    private final UserRepository userRepository;
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;

    public DemandProjectService(DemandProjectRepository demandProjectRepository, UserRepository userRepository, DemandItemRepository demandItemRepository) {
        this.userRepository = userRepository;
        this.demandProjectRepository = demandProjectRepository;
        this.demandItemRepository = demandItemRepository;
    }

    //참여폼: 굿즈 소개(이미지 3개) 조회
    public DemandProjectImgResponseDto getDemandProjectImg(Long demand_project_id){
        DemandProject demandProject = demandProjectRepository.findByDemandProject_Id(demand_project_id);
        return new DemandProjectImgResponseDto(demandProject);
    }

    //참여폼: 상세 정보 조회
    public DemandProjectInfoResponseDto getDemandProjectInfo(Long demand_project_id){
        DemandProject demandProject = demandProjectRepository.findByDemandProject_Id(demand_project_id);
        demandProjectRepository.updateView(demand_project_id); //상세정보 조회할 때마다 조회수 +1
        return new DemandProjectInfoResponseDto(demandProject, demandItemRepository.getTotalOrderCountByDemandProject(demandProject), demandItemRepository.getTotalGoalByDemandProject(demandProject));
    }
}