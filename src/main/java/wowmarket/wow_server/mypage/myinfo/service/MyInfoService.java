package wowmarket.wow_server.mypage.myinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myinfo.dto.GetLikeProjectDto;
import wowmarket.wow_server.mypage.myinfo.dto.LikedProjectDto;
import wowmarket.wow_server.mypage.myinfo.dto.MyInfoResponseDto;
import wowmarket.wow_server.repository.DemandLikesRepository;
import wowmarket.wow_server.repository.LikesRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyInfoService {
    private final LikesRepository likesRepository;
    private final DemandLikesRepository demandLikesRepository;

    public MyInfoResponseDto findMyInfo(User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 정보 없음");
        MyInfoResponseDto responseDto = MyInfoResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .univ(user.getUniv())
                .build();
        return responseDto;
    }

    public GetLikeProjectDto getLikeProjects(User user, Pageable pageable) {
        Page<Project> findProjects = likesRepository.findLikedProjects(user, pageable);
        Page<LikedProjectDto> projectList = findProjects.map(LikedProjectDto::new);
        return new GetLikeProjectDto(projectList.getTotalPages(), projectList.getNumber(), projectList.getContent());
    }

    public GetLikeProjectDto getLikeDemandProjects(User user, Pageable pageable) {
        Page<DemandProject> findDemandProjects = demandLikesRepository.findLikedDemandProjects(user, pageable);
        Page<LikedProjectDto> projectList = findDemandProjects.map(LikedProjectDto::new);
        return new GetLikeProjectDto(projectList.getTotalPages(), projectList.getNumber(), projectList.getContent());
    }
}
