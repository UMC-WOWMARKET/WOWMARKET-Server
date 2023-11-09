package wowmarket.wow_server.register.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterProjectDto {
    @NotNull
    private String project_name;
    @NotNull
    private String description;
    @NotNull
    private Long category_id;

    @NotNull
    private String thumbnail;
    @NotNull
    private String image1;
    private String image2;
    private String image3;

    @NotNull
    private List<RegisterItemDto> item;
    @NotNull
    private LocalDate start_date;
    @NotNull
    private LocalDate end_date;
    @NotNull
    private String receive_type;
    private String address;
    private Long delivery_fee;
//    private String address_detail; 추후에 추가 예정
//    private String zipcode;
    @NotNull
    private String bank;
    @NotNull
    private String account;
    @NotNull
    private String account_holder_name;
    @NotNull
    private String nickname;
    @NotNull
    private Boolean sell_to;


    @Builder
    public Project toEntity(){
        return Project.builder()
                .name(project_name)
                .description(description)
                .thumbnail(thumbnail)
                .image1(image1)
                .image2(image2)
                .image3(image3)
                .startDate(start_date)
                .endDate(end_date)
                .isDel(Boolean.FALSE)
                .isEnd(Boolean.FALSE)
                .receive_type(receive_type)
                .receive_address(address)
                .delivery_fee(delivery_fee)
                .bank(bank)
                .account(account)
                .account_holder_name(account_holder_name)
                .nickname(nickname)
                .sellTo(sell_to)
                .build();
    }
}
