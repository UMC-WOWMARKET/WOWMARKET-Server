package wowmarket.wow_server.mypage.myorder.sales.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyOrderFormListResponseDto {
    private List<MyOrderFormResponseDto> orderList;
    private int totalpage;
    private int currentpage;

    public MyOrderFormListResponseDto(List<MyOrderFormResponseDto> newDtos, int totalpage, int currentpage){
        this.orderList = newDtos;
        this.totalpage = totalpage;
        this.currentpage = currentpage;
    }
}
