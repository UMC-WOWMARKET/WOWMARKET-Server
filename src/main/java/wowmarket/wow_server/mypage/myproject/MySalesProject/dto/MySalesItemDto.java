package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Item;

@Getter
@NoArgsConstructor
public class MySalesItemDto {
    private Long itemId;
    private String name;
    private Long price;
    private int goal;

    public MySalesItemDto(Item item){
        this.itemId = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.goal = item.getGoal();
    }
}
