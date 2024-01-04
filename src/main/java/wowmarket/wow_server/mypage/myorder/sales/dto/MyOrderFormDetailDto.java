package wowmarket.wow_server.mypage.myorder.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.OrderDetail;

@Getter
@NoArgsConstructor
public class MyOrderFormDetailDto {
    private Long itemId;
    private String itemName;
    private Long price;
    private int count;

    public MyOrderFormDetailDto(OrderDetail orderDetail){
        this.itemId = orderDetail.getItem().getId();
        this.itemName = orderDetail.getItem().getName();
        this.price = orderDetail.getItem().getPrice();
        this.count = orderDetail.getCount();
    }

}
