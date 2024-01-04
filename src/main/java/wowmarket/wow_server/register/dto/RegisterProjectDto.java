package wowmarket.wow_server.register.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.ReceiveType;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterProjectDto {
    @NotNull
    private String projectName;
    @NotNull
    private String description;
    @NotNull
    private Long category_id;
    @NotNull
    private String sellerName;  // 판매자명

    private String phoneNumber; // 전화번호
    private String email; // 이메일
    private String sellerEtc; // 기타 연락 수단

    @NotNull
    private String thumbnail;
    @NotNull
    private String image1;
    private String image2;
    private String image3;

    @NotNull
    private List<RegisterItemDto> item;
    private List<RegisterQuestionDto> questions;

    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    private String receiveType; //delivery, pickup, all
    private String receiveAddress; //직접수령 시 픽업장소
    private String deliveryType; // 배송 시 배송 방법
    private Long deliveryFee; // 배송비


    @NotNull
    private String bank; //은행명
    @NotNull
    private String account; //계좌
    @NotNull
    private String accountHolderName; //예금주
    @NotNull
    private Boolean sellToAll;


    @Builder
    public Project toEntity(){
        return Project.builder()
                .projectName(projectName)
                .description(description)
                .sellerName(sellerName)
                .phoneNumber(phoneNumber)
                .email(email)
                .sellerEtc(sellerEtc)
                .thumbnail(thumbnail)
                .image1(image1)
                .image2(image2)
                .image3(image3)
                .startDate(startDate)
                .endDate(endDate)
                .participant_number(0)
                .receive_type(ReceiveType.valueOf(receiveType))
                .receive_address(receiveAddress)
                .deliveryType(deliveryType)
                .delivery_fee(deliveryFee)
                .bank(bank)
                .account(account)
                .account_holder_name(accountHolderName)
                .sellToAll(sellToAll)
                .isDel(Boolean.FALSE)
                .isEnd(Boolean.FALSE)
                .final_achievement_rate(0L)
                .build();
    }
}
