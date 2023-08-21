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
    private LocalDateTime createddate;
    private int status;

    public MySalesOrderDto(Orders orders){
        this.id = orders.getId();
        this.id = orders.getId();
        this.name = orders.getProject().getName();
        this.createddate = orders.getCreated_time();
        this.status = orders.getOrder_status();
    }
}
