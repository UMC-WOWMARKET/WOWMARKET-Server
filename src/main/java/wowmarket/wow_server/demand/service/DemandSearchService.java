package wowmarket.wow_server.demand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.demand.dto.DemandDto;
import wowmarket.wow_server.demand.dto.DemandResponseDto;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.DemandItemRepository;
import wowmarket.wow_server.repository.DemandProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemandSearchService {
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;
    private final UserRepository userRepository;

    public DemandResponseDto findDemandProjectSearch(String search, Pageable pageable, String univ, User user) {
        String user_univ = "allUniv";
        boolean user_univ_check = false;

        Page<DemandProject> findDemandProjects = new PageImpl<>(new ArrayList<>(), pageable, 0);

        if (user != null) {
            user_univ_check = user.isUniv_check();
            if (user_univ_check) {
                user_univ = user.getUniv();
            }
        }

        if (univ.equals("myUniv")) {
            if (user == null) { // 로그인 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
            } else if (user_univ_check) { // 학교인증 O -> 로그인 O
                findDemandProjects = demandProjectRepository.findBySearchUserUniv(search, user_univ, pageable);
            } else { // 학교인증 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "학교 인증이 필요한 서비스입니다.");
            }
        } else {
            findDemandProjects = demandProjectRepository.findBySearch(search, pageable);
        }

        Page<DemandDto> demandProjectDtos = findDemandProjects.map(demandProject -> new DemandDto(demandProject,
                demandItemRepository.getTotalOrderCountByDemandProject(demandProject),
                demandItemRepository.getTotalGoalByDemandProject(demandProject)));

        return new DemandResponseDto(user_univ,
                demandProjectDtos.getTotalPages(),
                demandProjectDtos.getNumber() + 1,
                demandProjectDtos.getContent());
    }
}
