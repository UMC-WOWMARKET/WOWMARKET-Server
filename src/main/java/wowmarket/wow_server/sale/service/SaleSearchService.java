package wowmarket.wow_server.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;
import wowmarket.wow_server.sale.dto.SaleResponseDto;
import wowmarket.wow_server.sale.dto.SaleDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleSearchService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    public SaleResponseDto findProjectSearch(String search, String univ, String orderby) {
        String user_univ = "allUniv";
        boolean user_univ_check = false;
        String loginUserEmail = SecurityUtil.getLoginUsername();

        List<Project> searchProjects = projectRepository.findByNameContaining(search);
        List<Project> univProjects = new ArrayList<>();
        List<Project> sortedProjects = new ArrayList<>();

        //로그인 상태에 따른 처리
        if (!loginUserEmail.equals("anonymousUser")) { //로그인 된 상태 + 학교 인증 : 소속학교 + 마감임박순
            System.out.println("[findProjectSearch] 로그인 O - 사용자 : " + loginUserEmail);
            User user = userRepository.findByEmail(loginUserEmail).get();
            user_univ_check = user.isUniv_check();
            if (user_univ_check) {
                user_univ = user.getUniv();
            }
        } else { //로그인이 안 된 상태이거나 학교 인증 미완 : 전체학교 + 마감임박순
            System.out.println("[findProjectSearch] 로그인 X - 사용자 : " + loginUserEmail);
        }

        //학교 필터링
        if (univ.equals("myUniv")) { // 학교 인증 확인
            if (loginUserEmail.equals("anonymousUser")) { // 로그인 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
            } else if (user_univ_check) { // 학교인증 O -> 로그인 O
                String stream_user_univ = user_univ;
                univProjects = searchProjects.stream()
                        .filter(project -> project.getUser().getUniv().equals(stream_user_univ))
                        .toList();
                System.out.println("[findProjectSearch] 소속학교 필터 : 학교 인증 && myUniv");
            } else { // 학교인증 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "학교 인증이 필요한 서비스입니다.");
            }
        } else {
            univProjects = searchProjects;
            System.out.println("[findProjectSearch] 전체학교 필터 : 학교 인증 X || 당연히 로그인 X || allUniv");
        }

        //정렬 처리
        if (orderby.equals("endDate")) { //default
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getEnd_date))
                    .toList();
            System.out.println("[findProjectSearch] 마감임박순 정렬");
        } else if (orderby.equals("startDate")) {
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getStart_date))
                    .toList();
            System.out.println("[findProjectSearch] 시작일자순 정렬");
        } else { //popularity
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getView))
                    .toList();
            System.out.println("[findProjectSearch] 인기순 정렬");
        }

        List<SaleDto> saleProjectDtos = sortedProjects.stream().map(project -> new SaleDto(project,
                        itemRepository.getTotalOrderCountByProject(project),
                        itemRepository.getTotalGoalByProject(project)))
                .toList();
        System.out.println("[findProjectSearch] List<SaleDto> saleProjectDtos 생성");

        return new SaleResponseDto(user_univ, saleProjectDtos);
    }
}
