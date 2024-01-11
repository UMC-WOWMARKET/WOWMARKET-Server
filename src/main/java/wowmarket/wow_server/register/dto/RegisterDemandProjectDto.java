package wowmarket.wow_server.register.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Permission;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDemandProjectDto {
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
    private Boolean sellToAll;



    @Builder
    public DemandProject toEntity(){
        return DemandProject.builder()
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
                .final_achievement_rate(0L)
                .isEnd(Boolean.FALSE)
                .sellToAll(sellToAll)
                .view(0)
                .permission(Permission.AWAIT)
                .build();
    }

}
