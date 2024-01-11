package wowmarket.wow_server.admin.permission.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectListResponse {
    private int totalPage;
    private int currentPage;
    private List<ProjectInfo> projectList;

    public ProjectListResponse(int totalPage, int currentPage, List<ProjectInfo> projectList) {
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.projectList = projectList;
    }
}
