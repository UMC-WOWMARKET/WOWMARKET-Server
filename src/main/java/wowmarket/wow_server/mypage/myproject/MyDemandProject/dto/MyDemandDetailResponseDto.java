package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandItemDto;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MyDemandDetailResponseDto {
    private Long projectid;
    private String projectname;
    private String description;
    private List<MyDemandItemDto> itemList;
    private String category;
    private LocalDate startdate;
    private LocalDate enddate;
    private String seller_nickname;

    public MyDemandDetailResponseDto(List<MyDemandItemDto> itemList, DemandProject demandProject){
        this.projectid = demandProject.getId();
        this.projectname = demandProject.getName();
        this.description = demandProject.getDescription();
        this.itemList = itemList;
        this.category = demandProject.getCategory().getName();
        this.startdate = demandProject.getStartDate();
        this.enddate = demandProject.getEndDate();
        this.seller_nickname = demandProject.getNickname();
    }

}
