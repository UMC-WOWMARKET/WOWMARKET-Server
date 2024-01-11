package wowmarket.wow_server.admin.permission.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProjectInfo {
    private Long projectId;
    private String projectName;
    private String projectUniv;
    private LocalDateTime createdTime;
    private String permission;

    public ProjectInfo(Project project) {
        this.projectId = project.getId();
        this.projectName = project.getProjectName();
        this.projectUniv = project.getUser().getUniv();
        this.createdTime = project.getCreated_time();
        this.permission = project.getPermission().toString();
    }

    public ProjectInfo(DemandProject demandProject) {
        this.projectId = demandProject.getId();
        this.projectName = demandProject.getProjectName();
        this.projectUniv = demandProject.getUser().getUniv();
        this.createdTime = demandProject.getCreated_time();
        this.permission = demandProject.getPermission().toString();
    }
}
