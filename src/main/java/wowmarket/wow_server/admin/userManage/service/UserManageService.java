package wowmarket.wow_server.admin.userManage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.admin.userManage.dto.UserManageDto;
import wowmarket.wow_server.admin.userManage.dto.UserSearchCond;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.repository.DemandProjectRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManageService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final DemandProjectRepository demandProjectRepository;

    public List<UserManageDto> getUserList(UserSearchCond cond, User admin){
        // 관리자만 조회 가능
        if (!admin.getRole().toString().equals("ROLE_ADMIN")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 동적 쿼리로 조건 따라 유저 검색
        List<User> userList = userRepository.getUserList(cond.getEmail(), cond.getName(), cond.getUniv());

        // Dto로 필요 데이터만 담아 리턴
        return userList.stream()
                .map(UserManageDto::from)
                .collect(Collectors.toList());
    }

    public void deleteUser(String email, User admin) {
        // 관리자만 삭제 가능
        if (!admin.getRole().toString().equals("ROLE_ADMIN")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 유저 isDel 변경
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        user.setDel(true);
        userRepository.save(user);

        // 유저 아이디가 일치하는 프로젝트 isDel 변경
        Long userId = user.getId();
        List<Project> projects = projectRepository.findByUser_Id(userId);
        projects.forEach(project -> {
            project.setDel(true);
            projectRepository.save(project);
        });

        List<DemandProject> demandProjects = demandProjectRepository.findByUser_Id(userId);
        demandProjects.forEach(demandProject -> {
            demandProject.setDel(true);
            demandProjectRepository.save(demandProject);
        });

    }
}
