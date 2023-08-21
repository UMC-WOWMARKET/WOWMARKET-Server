package wowmarket.wow_server.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;
import wowmarket.wow_server.sale.dto.SaleResponseDto;
import wowmarket.wow_server.sale.dto.SaleDto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleSearchService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    public SaleResponseDto findProjectHome(String search, Pageable pageable, String univ, User user) {
        String user_univ = "allUniv";
        boolean user_univ_check = false;
        LocalDate current_date = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Page<Project> findProjects = new PageImpl<>(new ArrayList<>(), pageable, 0);

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
                findProjects = projectRepository.findBySearchUserUniv(current_date, search, user_univ, pageable);
            } else { // 학교인증 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "학교 인증이 필요한 서비스입니다.");
            }
        } else if (univ.equals("allUniv")) {
            findProjects = projectRepository.findBySearch(current_date, search, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 필터링 값이 아닙니다.");
        }

        Page<SaleDto> projectDtos = findProjects.map(project -> new SaleDto(project,
                itemRepository.getTotalOrderCountByProject(project),
                itemRepository.getTotalGoalByProject(project)));

        return new SaleResponseDto(user_univ,
                projectDtos.getTotalPages(), projectDtos.getNumber(), projectDtos.getContent());
    }
}
