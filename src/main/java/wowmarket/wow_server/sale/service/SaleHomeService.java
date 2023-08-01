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
import wowmarket.wow_server.sale.dto.SaleListResponseDto;
import wowmarket.wow_server.sale.dto.SaleResponseDto;

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

    public SaleListResponseDto findSaleProjectHome(String univ, String orderby) {
        String user_univ = "";
        boolean user_univ_check = false;
        List<Project> allProjects = projectRepository.findAll();
        List<Project> univProjects = new ArrayList<>();
        List<Project> sortedProjects = new ArrayList<>();

        String loginUserEmail = SecurityUtil.getLoginUsername();

        if (!loginUserEmail.equals("anonymousUser")) { //로그인 된 상태
            User user = userRepository.findByEmail(loginUserEmail).get();
            user_univ = user.getUniv();
            user_univ_check = user.isUniv_check();
        }

        //사용자의 학교 인증 미완 시 학교 필터 all만 가능 myUniv 불가 -> 프론트에서 사용자애게 알려주기
        if (user_univ_check && univ.equals("myUniv")) {
            //학교 필터 myUniv
            String stream_user_univ = user_univ;
            univProjects = allProjects.stream()
                    .filter(project -> project.getUser().getUniv().equals(stream_user_univ))
                    .toList();

        } else { // !user.isUniv_check() || univ.equals("allUniv")
            univProjects = allProjects;
        }

//        아예 프론트에서 orderby를 End_date, Start_date 이렇게 넘겨주면 간단할 듯!!
//        이렇게 사용하는 방법은 없을까?
//        String orderMethod = "get" + orderby;
//        sortedProjects = univProjects.stream()
//                .sorted(Comparator.comparing(Project::orderMethod)).toList();

//        //정렬 조건 endDate, popularity, startDate
        if (orderby.equals("endDate")) { //default
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getEnd_date)).toList();
        } else if (orderby.equals("startDate")) {
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getStart_date)).toList();
        } else { //popularity
            sortedProjects = univProjects.stream()
                    .sorted(Comparator.comparing(Project::getView).reversed()).toList();
        }

        List<SaleResponseDto> saleProjectDtos = sortedProjects.stream()
                .map(project -> new SaleResponseDto(project,
                        itemRepository.getTotalOrderCountByProject(project),
                        itemRepository.getTotalGoalByProject(project)))
                .toList();

        return new SaleListResponseDto(user_univ, saleProjectDtos);
    }
}
