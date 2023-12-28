package wowmarket.wow_server.mypage.myproject.MySalesOrder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.OrderDetail;

@Getter
@NoArgsConstructor
public class MySalesOrderDetailDto {
    private Long item_id;
    private String name;
    private Long price;
    private int count;

    public MySalesOrderDetailDto(OrderDetail orderDetail){
        this.item_id = orderDetail.getItem().getId();
        this.name = orderDetail.getItem().getName();
        this.price = orderDetail.getItem().getPrice();
        this.count = orderDetail.getCount();
    }
}
