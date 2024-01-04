package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyDemandDto {
    private Long id;
    private String name;
    private LocalDateTime createdTime;
    private int status;

    public MyDemandDto(DemandProject demandProject){
        this.id = demandProject.getId();
        this.name = demandProject.getProjectName();
        this.createdTime = demandProject.getCreated_time();
        this.status = (demandProject.isEnd() == false ? 0 : 1);
    }
}
