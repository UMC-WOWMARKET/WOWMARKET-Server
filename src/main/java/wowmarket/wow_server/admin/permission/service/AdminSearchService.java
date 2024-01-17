package wowmarket.wow_server.admin.permission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.admin.permission.dto.response.ProjectInfo;
import wowmarket.wow_server.admin.permission.dto.response.ProjectListResponse;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.DemandProjectRepository;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminSearchService {
    private final ProjectRepository projectRepository;
    private final DemandProjectRepository demandProjectRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    //Admin Search
    public ProjectListResponse findProjectAdmin(String search, Pageable pageable, String univ) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (user.getRole().toString() != "ROLE_ADMIN") { //관리자가 아니면
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 접근 가능합니다.");
        }

        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Page<Project> findProjects = new PageImpl<>(new ArrayList<>(), pageable, 0);
        findProjects = projectRepository.findByAdminFormSearch(currentDate, search, univ, pageable);

        Page<ProjectInfo> projectDtos = findProjects.map(project -> new ProjectInfo(project));

        return new ProjectListResponse(projectDtos.getTotalPages(), projectDtos.getNumber(), projectDtos.getContent());
    }
}
