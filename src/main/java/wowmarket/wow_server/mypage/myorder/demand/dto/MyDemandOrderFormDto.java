package wowmarket.wow_server.mypage.myorder.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandOrder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyDemandOrderFormDto {
    private Long demand_order_id;
    private String name;
    private LocalDateTime createdtime;
    private int is_del;
    private String description;
    private String thumbnail;

    public MyDemandOrderFormDto(DemandOrder order){
        this.demand_order_id = order.getId();
        this.name = order.getDemandProject().getProjectName();
        this.createdtime = order.getCreated_time();
        this.is_del = order.getIsDel();
        this.description = order.getDemandProject().getDescription();
        this.thumbnail = order.getDemandProject().getThumbnail();

    }
}
