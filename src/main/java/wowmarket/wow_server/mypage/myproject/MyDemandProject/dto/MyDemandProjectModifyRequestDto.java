package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MyDemandProjectModifyRequestDto {
    private String projectName;
    private String description;
    private String sellerName;
    private String sellerPhoneNumber;
    private String sellerEmail;
    private String sellerEtc;
    private String thumbnail;
    private Long categoryId;
    private String image1;
    private String image2;
    private String image3;
    private List<MyDemandItemDto> itemList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
