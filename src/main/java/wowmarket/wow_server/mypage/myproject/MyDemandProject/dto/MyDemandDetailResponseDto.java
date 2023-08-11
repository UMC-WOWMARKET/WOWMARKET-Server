package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandItemDto;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MyDemandDetailResponseDto {
    private Long projectId;
    private String projectname;
    private String description;
    private String thumbnail;
    private List<MyDemandItemDto> itemList;
    private String category;
    private String image1;
    private String image2;
    private String image3;
    private LocalDate startdate;
    private LocalDate enddate;
    private String seller_nickname;

    public MyDemandDetailResponseDto(List<MyDemandItemDto> itemList, DemandProject demandProject){
        this.projectId = demandProject.getId();
        this.projectname = demandProject.getName();
        this.description = demandProject.getDescription();
        this.thumbnail = demandProject.getThumbnail();
        this.itemList = itemList;
        this.category = demandProject.getCategory().getName();
        this.image1 = demandProject.getImage1();
        this.image2 = demandProject.getImage2();
        this.image3 = demandProject.getImage3();
        this.startdate = demandProject.getStartDate();
        this.enddate = demandProject.getEndDate();
        this.seller_nickname = demandProject.getNickname();
    }

}
