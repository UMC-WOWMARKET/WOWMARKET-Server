package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import wowmarket.wow_server.domain.DemandProject;

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
        this.startdate = demandProject.getStart_date();
        this.enddate = demandProject.getEnd_date();
        this.seller_nickname = demandProject.getNickname();
    }

}
