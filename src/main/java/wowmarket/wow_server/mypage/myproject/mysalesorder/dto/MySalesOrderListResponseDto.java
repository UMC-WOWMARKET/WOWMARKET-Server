package wowmarket.wow_server.mypage.myproject.mysalesorder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.mypage.myproject.mysalesorder.dto.MySalesOrderDto;

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
