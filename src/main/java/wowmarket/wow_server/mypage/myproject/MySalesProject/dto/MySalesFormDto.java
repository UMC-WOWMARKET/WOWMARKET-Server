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
    private LocalDateTime createdtime;
    private int status;

    public MySalesFormDto(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.createdtime = project.getCreated_time();
        this.status = (project.isEnd() == false? 0 : 1);
    }
}
