package wowmarket.wow_server.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class SaleHomeService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    public SaleResponseDto findProjectHome(String univ, String orderby) {
        String user_univ = "allUniv";
        boolean user_univ_check = false;
        List<Project> allProjects = projectRepository.findAll();
        List<Project> univProjects = new ArrayList<>();
        List<Project> sortedProjects = new ArrayList<>();

        String loginUserEmail = SecurityUtil.getLoginUsername();

        //로그인 상태에 따른 처리
        if (!loginUserEmail.equals("anonymousUser")) { //로그인 된 상태
            System.out.println("[findProjectHome] 로그인 O - 사용자 : " + loginUserEmail);
            User user = userRepository.findByEmail(loginUserEmail).get();
            user_univ = user.getUniv();
            user_univ_check = user.isUniv_check();
        } else {
            System.out.println("[findProjectHome] 로그인 X - 사용자 : " + loginUserEmail);
        }

        //사용자의 학교 인증 미완 시 학교 필터 all만 가능 myUniv 불가 -> 프론트에서 사용자애게 알려주기
        if (user_univ_check && univ.equals("myUniv")) {
            String stream_user_univ = user_univ;
            univProjects = allProjects.stream()
                    .filter(project -> project.getUser().getUniv().equals(stream_user_univ))
                    .toList();
            System.out.println("[findProjectHome] 소속학교 필터 : 학교 인증 && myUniv");
        } else { // !user.isUniv_check() || univ.equals("allUniv")
            univProjects = allProjects;
            System.out.println("[findProjectHome] 전체학교 필터 : 학교 인증 X || 당연히 로그인 X || allUniv");
        }

        //정렬 처리 - endDate, popularity, startDate
        if (orderby.equals("endDate")) { //default
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getEnd_date)).toList();
            System.out.println("[findProjectHome] 마감임박순 정렬");
        } else if (orderby.equals("startDate")) {
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getStart_date)).toList();
            System.out.println("[findProjectHome] 시작일자순 정렬");
        } else { //popularity
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getView).reversed()).toList();
            System.out.println("[findProjectHome] 인기순 정렬");
        }

        List<SaleDto> projectDtos = sortedProjects.stream()
                .map(project -> new SaleDto(project,
                        itemRepository.getTotalOrderCountByProject(project),
                        itemRepository.getTotalGoalByProject(project)))
                .toList();
        System.out.println("[findProjectHome] List<SaleDto> projectDtos 생성");

        return new SaleResponseDto(user_univ, projectDtos);
    }
}
