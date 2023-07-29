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
    private String name;
    private Long price;
    private int goal;

    @Builder
    public Item toItemEntity(){
        return Item.builder()
                .name(name)
                .price(price)
                .goal(goal)
                .build();
    }

    @Builder
    public DemandItem toDemandItemEntity(){
        return DemandItem.builder()
                .name(name)
                .price(price)
                .goal(goal)
                .build();
    }
}
