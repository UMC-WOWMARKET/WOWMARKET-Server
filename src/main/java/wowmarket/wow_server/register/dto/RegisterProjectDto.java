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
    @NotNull
    private LocalDateTime start_date;
    @NotNull
    private LocalDateTime end_date;
    @NotNull
    private String receive_type; //delivery, pickup
    private String receive_address; //직접수령 시 픽업장소
    private String deliveryType; // 배송 시 배송 방법
    private Long delivery_fee; // 배송비


    @NotNull
    private String bank; //은행명
    @NotNull
    private String account; //계좌
    @NotNull
    private String account_holder_name; //예금주
    @NotNull
    private Boolean sell_to_all;


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
                .startDate(start_date)
                .endDate(end_date)
                .participant_number(0)
                .receive_type(ReceiveType.valueOf(receive_type))
                .receive_address(receive_address)
                .deliveryType(deliveryType)
                .delivery_fee(delivery_fee)
                .bank(bank)
                .account(account)
                .account_holder_name(account_holder_name)
                .sellToAll(sell_to_all)
                .isDel(Boolean.FALSE)
                .isEnd(Boolean.FALSE)
                .build();
    }
}
