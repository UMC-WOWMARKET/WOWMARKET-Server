package wowmarket.wow_server.mypage.myorder.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandOrder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyDemandOrderFormDto {
    private Long demandOrderId;
    private String name;
    private LocalDateTime createdTime;
    private int status;
    private String description;
    private String thumbnail;

    public MyDemandOrderFormDto(DemandOrder order){
        this.demandOrderId = order.getId();
        this.name = order.getDemandProject().getProjectName();
        this.createdTime = order.getCreated_time();
        this.status = order.getStatus();
        this.description = order.getDemandProject().getDescription();
        this.thumbnail = order.getDemandProject().getThumbnail();

    }
}
