package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyDemandDto {
    private Long demandid;
    private String name;
    private LocalDateTime createdtime;
    private boolean isend;

    public MyDemandDto(DemandProject demandProject){
        this.demandid = demandProject.getId();
        this.name = demandProject.getName();
        this.createdtime = demandProject.getCreated_time();
        this.isend = demandProject.isEnd();
    }
}
