package wowmarket.wow_server.mypage.myorder.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandDetail;

@Getter
@NoArgsConstructor
public class MyOrderDemandItemDto {
    private Long itemId;
    private String itemName;
    private Long price;
    private int count;

    public MyOrderDemandItemDto(DemandDetail demandDetail){
        this.itemId = demandDetail.getDemandItem().getId();
        this.itemName = demandDetail.getDemandItem().getName();
        this.price = demandDetail.getDemandItem().getPrice();
        this.count = demandDetail.getCount();
    }
}
