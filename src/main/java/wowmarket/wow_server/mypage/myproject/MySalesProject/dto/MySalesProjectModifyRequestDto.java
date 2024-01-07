package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Category;
import wowmarket.wow_server.domain.ReceiveType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesProjectModifyRequestDto {
    private String projectName;
    private String description;
    private String thumbnail;
    private int categoryId;
    private String image1;
    private String image2;
    private String image3;
    private List<MySalesItemDto> itemList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long receiveType;
    private String receiveAddress;
    private String sellerBank;
    private String sellerAccount;
    private String sellerAccountName;
    private String sellerName;
    private Long deliveryFee;
    private String sellerPhoneNumber;
    private String sellerEmail;
    private String sellerEtc;

    @Builder
    public MySalesProjectModifyRequestDto(String projectName, String description, String sellerName, String phoneNumber, String email, String sellerEtc,
                                          int categoryId, String thumbnail, String image1, String image2, String image3, LocalDateTime startDate,
                                          LocalDateTime endDate, Long receiveType, String receiveAddress, Long deliveryFee, String bank, String account,
                                          String accountHolderName, List<MySalesItemDto> itemList){
        this.projectName = projectName;
        this.description = description;
        this.sellerName = sellerName;
        this.sellerPhoneNumber = phoneNumber;
        this.sellerEmail = email;
        this.sellerEtc = sellerEtc;
        this.categoryId = categoryId;
        this.thumbnail = thumbnail;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.startDate = startDate;
        this.endDate = endDate;
        this.receiveType = receiveType;
        this.receiveAddress = receiveAddress;
        this.deliveryFee = deliveryFee;
        this.sellerBank = bank;
        this.sellerAccount = account;
        this.sellerAccountName = accountHolderName;
        this.itemList = itemList;
    }
}
