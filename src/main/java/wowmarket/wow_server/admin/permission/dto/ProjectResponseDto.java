package wowmarket.wow_server.admin.permission.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.admin.permission.dto.response.ProjectInfo;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectResponseDto {
    private String userUniv;
    private int totalPage;
    private int currentPage;
    private List<ProjectInfo> projectList;

    public ProjectResponseDto(String userUniv, int totalPage, int currentPage, List<ProjectInfo> newDtos) {
        this.userUniv = userUniv;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.projectList = newDtos;
    }
}
