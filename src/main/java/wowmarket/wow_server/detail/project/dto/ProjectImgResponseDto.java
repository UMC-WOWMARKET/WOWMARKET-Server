package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.Project;

@Getter
public class ProjectImgResponseDto {
    private String image1;
    private String image2;
    private String image3;

    public ProjectImgResponseDto(Project project) {
        this.image1=project.getImage1();
        this.image2=project.getImage2();
        this.image3=project.getImage3();
    }
}