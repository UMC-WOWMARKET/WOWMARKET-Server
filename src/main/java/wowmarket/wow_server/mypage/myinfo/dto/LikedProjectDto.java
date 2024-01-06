package wowmarket.wow_server.mypage.myinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Project;

@Getter
@NoArgsConstructor
public class LikedProjectDto {
    private Long projectId;
    private String sellerUniv;
    private String projectName;
    private String thumbnail;
    private boolean isEnd;
    private boolean isDel;

    @Builder
    public LikedProjectDto(Project project) {
        this.projectId = project.getId();
        this.sellerUniv = project.getUser().getUniv();
        this.projectName = project.getProjectName();
        this.thumbnail = project.getThumbnail();
        this.isEnd = project.isEnd();
        this.isDel = project.isDel();
    }

    @Builder
    public LikedProjectDto(DemandProject demandProject) {
        this.projectId = demandProject.getId();
        this.sellerUniv = demandProject.getUser().getUniv();
        this.projectName = demandProject.getProjectName();
        this.thumbnail = demandProject.getThumbnail();
        this.isEnd = demandProject.isEnd();
        this.isDel = false;
    }
}
