package wowmarket.wow_server.mypage.myproject.MySalesOrder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MySalesOrderDto {
    private Long id;
    private String name;
    private LocalDateTime createdtime;
    private int status;

    public MySalesOrderDto(Orders orders){
        this.id = orders.getId();
        this.id = orders.getId();
        this.name = orders.getProject().getName();
        this.createdtime = orders.getCreated_time();
        this.status = orders.getOrder_status();
    }
}
