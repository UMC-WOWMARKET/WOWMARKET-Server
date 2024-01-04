package wowmarket.wow_server.mypage.myorder.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandOrder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MyOrderDemandDetailResponseDto {
    private Long demandOrderId;
    private LocalDateTime createdDate;
    private int status;
    private String projectName;
    private String description;
    private String thumbnail;
    private List<MyOrderDemandItemDto> itemList;

    public MyOrderDemandDetailResponseDto(List<MyOrderDemandItemDto> itemList, DemandOrder demandOrder){
        this.demandOrderId = demandOrder.getId();
        this.createdDate = demandOrder.getCreated_time();
        this.status = demandOrder.getStatus();
        this.projectName = demandOrder.getDemandProject().getProjectName();
        this.description = demandOrder.getDemandProject().getDescription();
        this.thumbnail = demandOrder.getDemandProject().getThumbnail();
        this.itemList = itemList;
    }
}
