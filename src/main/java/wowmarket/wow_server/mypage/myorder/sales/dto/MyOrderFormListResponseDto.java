package wowmarket.wow_server.mypage.myorder.sales.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyOrderFormListResponseDto {
    private List<MyOrderSalesResponseDto> orderList;
    private int totalPage;
    private int currentPage;

    public MyOrderFormListResponseDto(List<MyOrderSalesResponseDto> newDtos, int totalpage, int currentpage){
        this.orderList = newDtos;
        this.totalPage = totalpage;
        this.currentPage = currentpage;
    }
}
