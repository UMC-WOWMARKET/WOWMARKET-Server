package wowmarket.wow_server.mypage.myorder.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyDemandOrderFormListResponseDto {
    //나의 수요조사 주문폼 전체보기 리스트 dto
    private List<MyDemandOrderFormDto> demand_order_list;
    private int total_page;
    private int current_page;

    public MyDemandOrderFormListResponseDto(List<MyDemandOrderFormDto> demand_order_list, int total_page, int current_page){
        this.demand_order_list = demand_order_list;
        this.total_page = total_page;
        this.current_page = current_page;
    }
}
