package wowmarket.wow_server.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.sale.dto.SaleListResponseDto;
import wowmarket.wow_server.sale.dto.SaleResponseDto;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleSearchService {
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    public SaleListResponseDto findProjectBySearch(String search) {
        String user_univ = "";
        List<Project> searchProjects = projectRepository.findByNameContaining(search);

        //로그인이 된 상태 : 소속학교 + 마감임박순
        List<Project> univProjects = searchProjects.stream()
                .filter(project -> project.getUser().getUniv().equals(user_univ))
                .toList();

        //로그인이 안 된 상태이거나 학교 인증 미완 : 전체학교 + 마감임박순
        //univProjects = searchProjects;

        List<Project> sortedProjects = univProjects.stream().sorted(Comparator.comparing(Project::getEnd_date)).toList();

        List<SaleResponseDto> saleProjectDtos = sortedProjects.stream().map(project -> new SaleResponseDto(project,
                        itemRepository.getTotalOrderCountByProject(project),
                        itemRepository.getTotalGoalByProject(project)))
                .toList();

        return new SaleListResponseDto(user_univ, saleProjectDtos);

    }
}
