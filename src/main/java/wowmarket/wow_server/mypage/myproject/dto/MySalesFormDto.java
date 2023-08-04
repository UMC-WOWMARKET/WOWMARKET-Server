package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MySalesFormDto {
    private Long projectid;
    private String name;
    private LocalDateTime createdtime;
    private boolean is_end;

    public MySalesFormDto(Project project){
        this.projectid = project.getId();
        this.name = project.getName();
        this.createdtime = project.getCreated_time();
        this.is_end = project.is_end();
    }
}
