package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyDemandOrderDto {
    private Long demandid;
    private String name;
    private LocalDateTime createdtime;
    private boolean isend;

    public MyDemandOrderDto(DemandProject demandProject){
        this.demandid = demandProject.getId();
        this.name = demandProject.getName();
        this.createdtime = demandProject.getCreated_time();
        this.isend = demandProject.is_end();
    }
}
