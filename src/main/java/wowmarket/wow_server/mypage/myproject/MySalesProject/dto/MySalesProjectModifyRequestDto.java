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
    private Long categoryId;
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

}
