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
    private LocalDateTime createdtime;
    private int isend;

    public MyDemandDto(DemandProject demandProject){
        this.id = demandProject.getId();
        this.name = demandProject.getName();
        this.createdtime = demandProject.getCreated_time();
        this.isend = (demandProject.isEnd() == false ? 0 : 1);
    }
}
