package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Item;

@Getter
@NoArgsConstructor
public class MySalesItemDto {
    private Long itemId;
    private String itemName;
    private Long price;
    private int limits;
    private int goal;

    public MySalesItemDto(Item item){
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.price = item.getPrice();
        this.goal = item.getGoal();
        this.limits = item.getLimits();
    }
}
