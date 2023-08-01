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
public class SaleSearchService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    public SaleListResponseDto findProjectBySearch(String search) {
        String user_univ = "";
        boolean user_univ_check = false;
        String loginUserEmail = SecurityUtil.getLoginUsername();
        List<Project> searchProjects = projectRepository.findByNameContaining(search);
        List<Project> univProjects = new ArrayList<>();

        if (!loginUserEmail.equals("anonymousUser")) { //로그인 된 상태 + 학교 인증 : 소속학교 + 마감임박순
            User user = userRepository.findByEmail(loginUserEmail).get();
            user_univ = user.getUniv();
            user_univ_check = user.isUniv_check();
            if (user_univ_check) { // 학교 인증 확인
                String stream_user_univ = user_univ;
                univProjects = searchProjects.stream()
                        .filter(project -> project.getUser().getUniv().equals(stream_user_univ))
                        .toList();
            } else {
                univProjects = searchProjects;
            }
        } else { //로그인이 안 된 상태이거나 학교 인증 미완 : 전체학교 + 마감임박순
            univProjects = searchProjects;
        }

        List<Project> sortedProjects = univProjects.stream().sorted(Comparator.comparing(Project::getEnd_date)).toList();

        List<SaleResponseDto> saleProjectDtos = sortedProjects.stream().map(project -> new SaleResponseDto(project,
                        itemRepository.getTotalOrderCountByProject(project),
                        itemRepository.getTotalGoalByProject(project)))
                .toList();

        return new SaleListResponseDto(user_univ, saleProjectDtos);
    }
}
