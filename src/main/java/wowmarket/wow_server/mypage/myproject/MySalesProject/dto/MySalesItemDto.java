package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Item;

@Getter
@NoArgsConstructor
public class MySalesItemDto {
    private Long itemid;
    private String name;
    private Long price;
    private int goal;
//    private String img;

    public MySalesItemDto(Item item){
        this.itemid = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.goal = item.getGoal();
    }
}
