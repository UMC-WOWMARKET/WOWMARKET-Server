package wowmarket.wow_server.demand.service;

import lombok.RequiredArgsConstructor;
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
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemandSearchService {
        private final DemandProjectRepository demandProjectRepository;
        private final DemandItemRepository demandItemRepository;
        private final UserRepository userRepository;

    public DemandResponseDto findDemandProjectSearch(String search, String univ, String orderby) {
        String user_univ = "allUniv";
        boolean user_univ_check = false;
        String loginUserEmail = SecurityUtil.getLoginUsername();

        List<DemandProject> searchDemandProjects = demandProjectRepository.findByNameContaining(search);
        List<DemandProject> univDemandProjects = new ArrayList<>();
        List<DemandProject> sortedDemandProjects = new ArrayList<>();

        //로그인 상태에 따른 처리
        if (!loginUserEmail.equals("anonymousUser")) {
            System.out.println("[findDemandProjectSearch] 로그인 O - 사용자 : " + loginUserEmail);
            User user = userRepository.findByEmail(loginUserEmail).get();
            user_univ = user.getUniv();
            user_univ_check = user.isUniv_check();
        } else {
            System.out.println("[findDemandProjectSearch] 로그인 X - 사용자 : " + loginUserEmail);
        }

        //학교 필터링
        if (univ.equals("myUniv")) { // 학교 인증 확인
            if (!loginUserEmail.equals("anonymousUser") && !user_univ_check) { // 로그인 O && 학교인증 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "학교 인증이 필요한 서비스입니다.");
            } else if (user_univ_check) {
                String stream_user_univ = user_univ;
                univDemandProjects = searchDemandProjects.stream()
                        .filter(demandProject -> demandProject.getUser().getUniv().equals(stream_user_univ))
                        .toList();
                System.out.println("[findDemandProjectSearch] 소속학교 필터 : 학교 인증 && myUniv");
            }
        } else {
            univDemandProjects = searchDemandProjects;
            System.out.println("[findDemandProjectSearch] 전체학교 필터 : 학교 인증 X || 당연히 로그인 X || allUniv");
        }

        //정렬 처리
        if (orderby.equals("endDate")) { //default
            sortedDemandProjects = univDemandProjects.stream()
                    .sorted(Comparator.comparing(DemandProject::getEnd_date))
                    .toList();
            System.out.println("[findDemandProjectSearch] 마감임박순 정렬");
        } else if (orderby.equals("startDate")) {
            sortedDemandProjects = univDemandProjects.stream()
                    .sorted(Comparator.comparing(DemandProject::getStart_date))
                    .toList();
            System.out.println("[findDemandProjectSearch] 시작일자순 정렬");
        } else { //popularity
            sortedDemandProjects = univDemandProjects.stream()
                    .sorted(Comparator.comparing(DemandProject::getView))
                    .toList();
            System.out.println("[findDemandProjectSearch] 인기순 정렬");
        }

        List<DemandDto> demandProjectDtos = sortedDemandProjects.stream().map(demandProject -> new DemandDto(demandProject,
                        demandItemRepository.getTotalOrderCountByDemandProject(demandProject),
                        demandItemRepository.getTotalGoalByDemandProject(demandProject)))
                .toList();
        System.out.println("[findDemandProjectSearch] List<DemandDto> demandProjectDtos 생성");

        return new DemandResponseDto(user_univ, demandProjectDtos);
    }
}
