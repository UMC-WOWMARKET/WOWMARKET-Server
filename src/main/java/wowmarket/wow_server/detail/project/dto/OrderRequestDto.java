package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long itemId; //수요조사 상품 번호
    private int count; //개수
}