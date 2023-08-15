package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.Item;

//주문폼 {상품, 판매가} 정보 불러오는 Dto

@Getter
public class ItemResponseDto {
    private Long id; //item_id
    private String name; //상품명
    private Long price; //판매가

    public ItemResponseDto(Item item) {
        this.id = item.getId(); //item_id 추가
        this.name = item.getName(); //상품명
        this.price = item.getPrice(); //판매가
   }
}