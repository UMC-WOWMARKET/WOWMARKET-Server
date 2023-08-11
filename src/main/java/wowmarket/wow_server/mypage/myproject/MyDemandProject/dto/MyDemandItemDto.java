package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandItem;

@Getter
@NoArgsConstructor
public class MyDemandItemDto {
    private Long itemId;
    private String name;
    private Long price;
    private int goal;

    public MyDemandItemDto(DemandItem demandItem){
        this.itemId = demandItem.getId();
        this.name = demandItem.getName();
        this.price = demandItem.getPrice();
        this.goal = demandItem.getGoal();
    }
}
