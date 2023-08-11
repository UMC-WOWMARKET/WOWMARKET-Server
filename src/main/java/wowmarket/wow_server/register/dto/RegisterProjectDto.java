package wowmarket.wow_server.register.dto;

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
    private String project_name;
    private String description;
    private Long category_id;
    private List<RegisterItemDto> item;
    private LocalDate start_date;
    private LocalDate end_date;
    private String receive_type;
    private String address;
//    private String address_detail; 추후에 추가 예정
//    private String zipcode;
    private String bank;
    private String account;
    private String account_holder_name;
    private String nickname;


    @Builder
    public Project toEntity(){
        return Project.builder()
                .name(project_name)
                .description(description)
                .startDate(start_date)
                .endDate(end_date)
                .isDel(Boolean.FALSE)
                .isEnd(Boolean.FALSE)
                .receive_type(receive_type)
                .address(address)
                .bank(bank)
                .account(account)
                .account_holder_name(account_holder_name)
                .nickname(nickname)
                .build();
    }
}
