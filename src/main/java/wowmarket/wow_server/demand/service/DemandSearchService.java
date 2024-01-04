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
import wowmarket.wow_server.repository.DemandItemRepository;
import wowmarket.wow_server.repository.DemandProjectRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemandSearchService {
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;

    public DemandResponseDto findDemandProjectSearch(String search, Pageable pageable, String univ, User user) {
        String userUniv = "allUniv";
        boolean userUnivCheck = false;
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Page<DemandProject> findDemandProjects = new PageImpl<>(new ArrayList<>(), pageable, 0);

        if (user != null) {
            userUnivCheck = user.isUniv_check();
            if (userUnivCheck) {
                userUniv = user.getUniv();
            }
        }

        if (univ.equals("myUniv")) {
            if (user == null) { // 로그인 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
            } else if (userUnivCheck) { // 학교인증 O -> 로그인 O
                findDemandProjects = demandProjectRepository.findBySearchUserUniv(currentDate, search, userUniv, pageable);
            } else { // 학교인증 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "학교 인증이 필요한 서비스입니다.");
            }
        } else if (univ.equals("allUniv")) {
            findDemandProjects = demandProjectRepository.findBySearch(currentDate, search, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 필터링 값이 아닙니다.");
        }

        Page<DemandDto> demandProjectDtos = findDemandProjects.map(demandProject -> new DemandDto(demandProject,
                demandItemRepository.getTotalOrderCountByDemandProject(demandProject),
                demandItemRepository.getTotalGoalByDemandProject(demandProject)));

        return new DemandResponseDto(userUniv,
                demandProjectDtos.getTotalPages(),
                demandProjectDtos.getNumber(),
                demandProjectDtos.getContent());
    }
}
