package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandProject;

@Getter
public class DemandProjectImgResponseDto {
    private String image1;
    private String image2;
    private String image3;

    public DemandProjectImgResponseDto(DemandProject project) {
        this.image1=project.getImage1();
        this.image2=project.getImage2();
        this.image3=project.getImage3();
    }
}