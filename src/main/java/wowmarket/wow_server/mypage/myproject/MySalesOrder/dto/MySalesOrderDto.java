package wowmarket.wow_server.mypage.myproject.MySalesOrder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MySalesOrderDto {
    private Long projectId;
    private Long orderId;
    private String projectname;
    private LocalDateTime createddate;
    private int order_status;
    private boolean isdel;

    public MySalesOrderDto(Orders orders){
        this.projectId = orders.getProject().getId();
        this.orderId = orders.getId();
        this.projectname = orders.getProject().getName();
        this.createddate = orders.getCreated_time();
        this.order_status = orders.getOrder_status();
        this.isdel = orders.isDel();
        this.isdel = orders.isDel();
    }
}
