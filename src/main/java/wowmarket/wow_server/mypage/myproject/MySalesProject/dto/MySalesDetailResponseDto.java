package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesDetailResponseDto {
    private Long projectId;
    private String projectName;
    private String description;
    private String thumbnail;
    private String category;
    private String image1;
    private String image2;
    private String image3;
    private List<MySalesItemDto> itemList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String receiveType;
    private String receiveAddress;
    private String sellerBank;
    private String sellerAccount;
    private String sellerAccountName;
    private String sellerName;
    private Long deliveryFee;
    private String sellerPhoneNumber;
    private String sellerEmail;
    private String sellerEtc;


    public MySalesDetailResponseDto(Project project, List<MySalesItemDto> itemDtos){
        this.projectId = project.getId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.thumbnail = project.getThumbnail();
        this.image1 = project.getImage1();
        this.image2 = project.getImage2();
        this.image3 = project.getImage3();
        this.category = project.getCategory().getName();
        this.itemList = itemDtos;
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.receiveType = project.getReceive_type().toString();
        this.receiveAddress = project.getReceive_address();
        this.sellerBank = project.getBank();
        this.sellerAccount = project.getAccount();
        this.sellerAccountName = project.getAccount_holder_name();
        this.sellerName = project.getSellerName();
        this.deliveryFee = project.getDelivery_fee();
        this.sellerPhoneNumber = project.getPhoneNumber();
        this.sellerEmail = project.getEmail();
        this.sellerEtc = project.getSellerEtc();
    }
}
