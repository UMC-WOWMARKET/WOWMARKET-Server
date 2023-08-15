package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandItem;

import java.time.LocalDate;

// 수요조사폼 작성시 상품명&가격&상품 이미지 받아오는 api

@Getter
public class DemandItemResponseDto {
    private Long id;
    private String name; //상품명
    private Long price; //판매가

    public DemandItemResponseDto(DemandItem item) {
        this.id = item.getId(); //demand_item_id 추가
        this.name = item.getName(); //상품명
        this.price = item.getPrice(); //판매가
    }
}