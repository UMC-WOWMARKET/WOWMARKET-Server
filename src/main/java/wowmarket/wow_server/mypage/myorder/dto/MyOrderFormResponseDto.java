package wowmarket.wow_server.mypage.myorder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import wowmarket.wow_server.domain.Orders;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyOrderFormResponseDto {
    private Long orderId;
    private String name;
    private LocalDateTime createdtime;
    private int status;
    private boolean isdel;

    public MyOrderFormResponseDto(Orders order){
        this.orderId = order.getId();
        this.name = order.getProject().getName();
        this.createdtime = order.getCreated_time();
        this.status = order.getOrder_status();
        this.isdel = order.isDel();
    }
}
