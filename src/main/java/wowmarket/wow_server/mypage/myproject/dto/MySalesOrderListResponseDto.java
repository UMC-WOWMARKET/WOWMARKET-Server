package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesOrderListResponseDto {
    private List<MySalesOrderDto> orderList;
    private int totalpage;
    private int currentpage;

    public MySalesOrderListResponseDto(List<MySalesOrderDto> orderList, int totalpage, int currentpage){
        this.orderList = orderList;
        this.totalpage = totalpage;
        this.currentpage = currentpage;
    }
}
