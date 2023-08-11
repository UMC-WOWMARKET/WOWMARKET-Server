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
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;
import wowmarket.wow_server.sale.dto.SaleResponseDto;
import wowmarket.wow_server.sale.dto.SaleDto;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleHomeService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    public SaleResponseDto findProjectHome(Pageable pageable, String univ) {
        String user_univ = "allUniv";
        boolean user_univ_check = false;
        String loginUserEmail = SecurityUtil.getLoginUsername();

        Page<Project> findProjects = new PageImpl<>(new ArrayList<>(), pageable, 0);

        //로그인 상태에 따른 처리
        if (!loginUserEmail.equals("anonymousUser")) { //로그인 된 상태
            System.out.println("[findProjectHome] 로그인 O - 사용자 : " + loginUserEmail);
            User user = userRepository.findByEmail(loginUserEmail).get();
            user_univ_check = user.isUniv_check();
            if (user_univ_check) {
                user_univ = user.getUniv();
            }
        } else {
            System.out.println("[findProjectHome] 로그인 X - 사용자 : " + loginUserEmail);
        }

        if (univ.equals("myUniv")) {
            if (loginUserEmail.equals("anonymousUser")) { // 로그인 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
            } else if (user_univ_check) { // 학교인증 O -> 로그인 O
                findProjects = projectRepository.findByUserUniv(user_univ, pageable);
                System.out.println("[findProjectHome] 소속학교 필터 : 학교 인증 && myUniv");
            } else { //학교인증 X
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "학교 인증이 필요한 서비스입니다.");
            }
        } else { // !user.isUniv_check() || univ.equals("allUniv")
            findProjects = projectRepository.findAllNotDelNotEnd(pageable);
            System.out.println("[findProjectHome] 전체학교 필터 : 학교 인증 X || 로그인 X || allUniv");
        }

        Page<SaleDto> projectDtos = findProjects.map(project -> new SaleDto(project,
                        itemRepository.getTotalOrderCountByProject(project),
                        itemRepository.getTotalGoalByProject(project)));

        return new SaleResponseDto(user_univ,
                projectDtos.getTotalPages(), projectDtos.getNumber() + 1, projectDtos.getContent());
    }
}