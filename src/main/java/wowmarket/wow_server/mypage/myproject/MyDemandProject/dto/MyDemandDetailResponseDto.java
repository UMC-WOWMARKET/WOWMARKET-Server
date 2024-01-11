package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandItemDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MyDemandDetailResponseDto {
    private Long projectId;
    private String projectName;
    private String description;

    private String sellerName;

    private String sellerPhoneNumber;
    private String sellerEmail;
    private String sellerEtc;


    private String thumbnail;
    private String category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<MyDemandItemDto> itemList;
    private String image1;
    private String image2;
    private String image3;

    private List<DemandAddtionalQuestionDto> demandQuestionList;

    public MyDemandDetailResponseDto(List<MyDemandItemDto> itemList, DemandProject demandProject, List<DemandAddtionalQuestionDto> demandAddtionalQuestionList){
        this.projectId = demandProject.getId();
        this.projectName = demandProject.getProjectName();
        this.description = demandProject.getDescription();

        this.sellerName = demandProject.getSellerName();

        this.sellerPhoneNumber = demandProject.getPhoneNumber();
        this.sellerEmail = demandProject.getEmail();
        this.sellerEtc = demandProject.getSellerEtc();

        this.thumbnail = demandProject.getThumbnail();
        this.category = demandProject.getCategory().getName();
        this.startDate = demandProject.getStartDate();
        this.endDate = demandProject.getEndDate();

        this.itemList = itemList;
        this.image1 = demandProject.getImage1();
        this.image2 = demandProject.getImage2();
        this.image3 = demandProject.getImage3();

        this.demandQuestionList = demandAddtionalQuestionList;
    }

}
