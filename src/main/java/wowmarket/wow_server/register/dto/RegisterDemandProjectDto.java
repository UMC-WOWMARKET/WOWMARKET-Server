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
    private String name;
    private String description;
    //대표이미지
    private Long category_id;
    private List<RegisterItemDto> item;
    //소개이미지
    private LocalDate start_date;
    private LocalDate end_date;


    @Builder
    public DemandProject toEntity(){
        return DemandProject.builder()
                .name(name)
                .description(description)
                .start_date(start_date)
                .end_date(end_date)
                .build();
    }

}
