
package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;

@Getter
public class DemandDetailRequestDto {
    private Long demandItemId; //수요조사 상품 번호
    private int count; //개수
}