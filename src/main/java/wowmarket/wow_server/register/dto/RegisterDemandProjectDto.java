package wowmarket.wow_server.register.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDemandProjectDto {
    private String project_name;
    private String description;
    private Long category_id;
    private List<RegisterItemDto> item;
    private LocalDate start_date;
    private LocalDate end_date;
    private String nickname;


    @Builder
    public DemandProject toEntity(){
        return DemandProject.builder()
                .name(project_name)
                .description(description)
                .startDate(start_date)
                .endDate(end_date)
                .nickname(nickname)
                .build();
    }

}
