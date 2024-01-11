package wowmarket.wow_server.admin.permission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.admin.permission.dto.request.PermissionProjectsRequest;
import wowmarket.wow_server.admin.permission.dto.response.ProjectInfo;
import wowmarket.wow_server.admin.permission.dto.response.ProjectListResponse;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Permission;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.repository.DemandProjectRepository;
import wowmarket.wow_server.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminPermissionService {
    private final ProjectRepository projectRepository;
    private final DemandProjectRepository demandProjectRepository;

    public ProjectListResponse getProjectList(int pageNo, PageRequest pageable) {
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Page<Project> findProjects = projectRepository.findProjectsAdmin(currentDate, pageable);
        Page<ProjectInfo> projectList = findProjects.map(ProjectInfo::new);

        return new ProjectListResponse(projectList.getTotalPages(), projectList.getNumber(), projectList.getContent());
    }

    @Transactional
    public ResponseEntity<Void> updateProjectPermission(String permission, PermissionProjectsRequest permissionProjectsRequest) {
        permissionProjectsRequest.getProjectIds()
                .forEach(projectId -> projectRepository.updatePermission(Permission.valueOf(permission), projectId));
        return ResponseEntity.ok().build();
    }

    public ProjectListResponse getDemandProjectList(int pageNo, PageRequest pageable) {
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Page<DemandProject> findDemandProjects = demandProjectRepository.findDemandProjectsPermission(currentDate, pageable);
        Page<ProjectInfo> projectList = findDemandProjects.map(ProjectInfo::new);

        return new ProjectListResponse(projectList.getTotalPages(), projectList.getNumber(), projectList.getContent());
    }

    @Transactional
    public ResponseEntity<Void> updateDemandProjectPermission(String permission, PermissionProjectsRequest permissionProjectsRequest) {
        permissionProjectsRequest.getProjectIds()
                .forEach(projectId -> demandProjectRepository.updatePermission(Permission.valueOf(permission), projectId));
        return ResponseEntity.ok().build();
    }
}
