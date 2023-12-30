package wowmarket.wow_server.detail.demandproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandProjectImgResponseDto;
import wowmarket.wow_server.detail.demandproject.dto.DemandProjectInfoResponseDto;
import wowmarket.wow_server.domain.DemandLikes;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Likes;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemandProjectService {
    private final UserRepository userRepository;
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;
    private final DemandLikesRepository demandLikesRepository;

    //참여폼: 굿즈 소개(이미지 3개) 조회
    public DemandProjectImgResponseDto getDemandProjectImg(Long demand_project_id){
        DemandProject demandProject = demandProjectRepository.findByDemandProject_Id(demand_project_id);
        return new DemandProjectImgResponseDto(demandProject);
    }

    //참여폼: 상세 정보 조회
    @Transactional
    public DemandProjectInfoResponseDto getDemandProjectInfo(Long demand_project_id, User user){
        boolean isLiked = false;
        DemandProject demandProject = demandProjectRepository.findByDemandProject_Id(demand_project_id);
        demandProjectRepository.updateView(demand_project_id); //상세정보 조회할 때마다 조회수 +1
        if (user != null && demandLikesRepository.findByUserAndDemandProject(user, demand_project_id).isPresent()) {
            isLiked = true;
        }
        return new DemandProjectInfoResponseDto(demandProject, demandItemRepository.getTotalOrderCountByDemandProject(demandProject),
                demandItemRepository.getTotalGoalByDemandProject(demandProject), isLiked);
    }

    @Transactional
    public ResponseEntity<?> likeDemandProject(Long demandProjectId, User user) {
        Optional<DemandLikes> demandLikeExist = demandLikesRepository.findByUserAndDemandProject(user, demandProjectId);
        if (demandLikeExist.isEmpty()) {
            demandLikesRepository.save(
                    DemandLikes.builder()
                            .user(user).demandProject(demandProjectRepository.findById(demandProjectId).get())
                            .build()
            );
            demandProjectRepository.updateDemandProjectLike(demandProjectId);
            userRepository.updateDemandProjectLike(user);
        } else {
            if (demandProjectRepository.findById(demandProjectId).get().getLikeCnt() > 0
                    && user.getDemandLike() > 0) {
                demandLikesRepository.deleteDemandLikes(demandLikeExist.get().getId());
                demandProjectRepository.updateDemandProjectUnLike(demandProjectId);
                userRepository.updateDemandProjectUnLike(user);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "like 수가 0이라 unlike할 수 없음");
            }
        }
        return ResponseEntity.ok().build();
    }
}