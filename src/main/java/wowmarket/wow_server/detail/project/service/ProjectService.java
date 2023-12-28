package wowmarket.wow_server.detail.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.project.dto.ProjectImgResponseDto;
import wowmarket.wow_server.detail.project.dto.ProjectInfoResponseDto;
import wowmarket.wow_server.domain.Likes;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.LikesRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;
    private final LikesRepository likesRepository;

    //주문폼: 굿즈 소개(이미지 3개) 조회
    public ProjectImgResponseDto getProjectImg(Long project_id) {
        Project project = projectRepository.findByProject_Id(project_id);
        return new ProjectImgResponseDto(project);
    }

    //주문폼: 상세 정보 조회
    public ProjectInfoResponseDto getProjectInfo(Long project_id) {
        Project project = projectRepository.findByProject_Id(project_id);
        projectRepository.updateView(project_id); //상세정보 조회할 때마다 조회수 +1
        return new ProjectInfoResponseDto(project, itemRepository.getTotalOrderCountByProject(project),
                itemRepository.getTotalGoalByProject(project));
    }

    @Transactional
    public ResponseEntity<?> likeProject(Long projectId, User user) {
        Optional<Likes> likeExist = likesRepository.findByUserAndProject(user, projectId);
        if (likeExist.isEmpty()) {
            likesRepository.save(
                    Likes.builder().user(user).project(projectRepository.findById(projectId).get()).build()
            );
            projectRepository.updateProjectLike(projectId);
            userRepository.updateProjectLike(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 like한 프로젝트");
        }
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> unLikeProject(Long projectId, User user) {
        Optional<Likes> likeExist = likesRepository.findByUserAndProject(user, projectId);
        if (likeExist.isPresent()) {
            if (projectRepository.findById(projectId).get().getLikeCnt() > 0
                    && user.getProjectLike() > 0) {
                likesRepository.deleteLikes(likeExist.get().getId());
                projectRepository.updateProjectUnLike(projectId);
                userRepository.updateProjectUnLike(user);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "like 수가 0이라 unlike할 수 없음");
            }
        } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "like하지 않아서 unlike할 수 없음");
        }
        return ResponseEntity.ok().build();
    }
}