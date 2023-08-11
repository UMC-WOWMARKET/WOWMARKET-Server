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
    private String thumbnail;
    private String image1;
    private String image2;
    private String image3;
    private List<RegisterItemDto> item;
    private LocalDate start_date;
    private LocalDate end_date;
    private String nickname;


    @Builder
    public DemandProject toEntity(){
        return DemandProject.builder()
                .name(project_name)
                .description(description)
                .thumbnail(thumbnail)
                .image1(image1)
                .image2(image2)
                .image3(image3)
                .startDate(start_date)
                .endDate(end_date)
                .nickname(nickname)
                .build();
    }

}
