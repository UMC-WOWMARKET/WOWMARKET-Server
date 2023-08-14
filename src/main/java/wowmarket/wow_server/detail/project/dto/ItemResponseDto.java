package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;
import wowmarket.wow_server.domain.Category;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;

// 주문폼 작성시 상품명&가격&상품 이미지 받아오는 api

@Getter
public class ItemResponseDto {
    private String name; //상품명
    private Long price; //판매가

    public ItemResponseDto(Item item) {
        this.name = item.getName(); //상품명
        this.price = item.getPrice(); //판매가
   }
}