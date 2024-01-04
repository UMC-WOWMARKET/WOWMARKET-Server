package wowmarket.wow_server.mypage.myorder.sales.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyOrderSalesResponseDto {
    private Long orderId;
    private String name;
    private LocalDateTime createdTime;
    private int status;
    private int isDel;
    private int price;
    private String description;
    private String thumbnail;

    public MyOrderSalesResponseDto(Orders order){
        this.orderId = order.getId();
        this.name = order.getProject().getProjectName();
        this.createdTime = order.getCreated_time();
        this.status = order.getOrder_status();
        this.isDel = order.getIsDel();
        this.price = order.getTotal_price();
        this.thumbnail = order.getProject().getThumbnail();
        this.description = order.getProject().getDescription();

    }
}
