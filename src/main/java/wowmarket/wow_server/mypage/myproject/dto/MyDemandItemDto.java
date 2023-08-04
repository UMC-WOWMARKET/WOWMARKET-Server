package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandItem;

@Getter
@NoArgsConstructor
public class MyDemandItemDto {
    private Long itemid;
    private String name;
    private Long price;
    private int goal;
    //사진추가하기
    public MyDemandItemDto(DemandItem demandItem){
        this.itemid = demandItem.getId();
        this.name = demandItem.getName();
        this.price = demandItem.getPrice();
        this.goal = demandItem.getGoal();
    }
}
