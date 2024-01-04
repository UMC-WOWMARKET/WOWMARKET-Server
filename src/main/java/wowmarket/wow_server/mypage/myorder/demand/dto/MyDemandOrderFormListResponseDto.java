package wowmarket.wow_server.mypage.myorder.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyDemandOrderFormListResponseDto {
    //나의 수요조사 주문폼 전체보기 리스트 dto
    private List<MyDemandOrderFormDto> demandOrderList;
    private int totalPage;
    private int currentPage;

    public MyDemandOrderFormListResponseDto(List<MyDemandOrderFormDto> demand_order_list, int total_page, int current_page){
        this.demandOrderList = demand_order_list;
        this.totalPage = total_page;
        this.currentPage = current_page;
    }
}
