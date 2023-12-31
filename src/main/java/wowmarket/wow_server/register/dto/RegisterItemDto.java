package wowmarket.wow_server.register.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandItem;
import wowmarket.wow_server.domain.Item;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterItemDto {
    private String item_name;
    private Long price;
    private int goal;
    private int limits;

    @Builder
    public Item toItemEntity(){
        return Item.builder()
                .name(item_name)
                .price(price)
                .goal(goal)
                .limits(limits)
                .build();
    }

    @Builder
    public DemandItem toDemandItemEntity(){
        return DemandItem.builder()
                .name(item_name)
                .price(price)
                .goal(goal)
                .limits(limits)
                .build();
    }
}
