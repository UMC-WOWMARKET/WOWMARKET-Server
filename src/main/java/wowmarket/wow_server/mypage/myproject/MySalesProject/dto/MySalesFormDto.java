package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MySalesFormDto {
    private Long id;
    private String name;
    private LocalDateTime createdTime;
    private int status;
    private String description;
    private String thumbnail;

    public MySalesFormDto(Project project){
        this.id = project.getId();
        this.name = project.getProjectName();
        this.createdTime = project.getCreated_time();
        this.status = (project.isEnd() == false? 0 : 1);
        this.description = project.getDescription();
        this.thumbnail = project.getThumbnail();
    }
}
