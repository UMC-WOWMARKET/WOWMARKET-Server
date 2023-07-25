package wowmarket.wow_server.mypage.myorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Order;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyOrderFormResponseDto {
    private Long orderid;
    private String name;
    private LocalDateTime createdtime;
    private int status;

    public MyOrderFormResponseDto(Order order){
        this.orderid = order.getId();
        this.name = order.getProject().getName();
        this.createdtime = order.getCreated_time();
        this.status = order.getOrder_status();
    }
}
