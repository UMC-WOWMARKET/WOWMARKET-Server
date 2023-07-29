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
    private String name;
    private String description;
    //대표이미지
    private Long category_id;
    private List<RegisterItemDto> item;
    //소개이미지
    private LocalDate start_date;
    private LocalDate end_date;
    private String receive_type;
    private String bank;
    private String account;
    private String nickname;
    private String inquired_phone;
    private String inquired_email;


    @Builder
    public Project toEntity(){
        return Project.builder()
                .name(name)
                .description(description)
                .start_date(start_date)
                .end_date(end_date)
                .is_del(Boolean.FALSE)
                .is_end(Boolean.FALSE)
                .receive_type(receive_type)
                .bank(bank)
                .account(account)
                .nickname(nickname)
                .inquired_phone(inquired_phone)
                .inquired_email(inquired_email)
                .build();
    }
}
