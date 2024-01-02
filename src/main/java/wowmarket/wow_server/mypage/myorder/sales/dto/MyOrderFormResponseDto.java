package wowmarket.wow_server.mypage.myorder.sales.dto;

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
    private int is_del;
    private int price;
    private String description;
    private String thumbnail;

    public MyOrderFormResponseDto(Orders order){
        this.orderId = order.getId();
        this.name = order.getProject().getProjectName();
        this.createdtime = order.getCreated_time();
        this.status = order.getOrder_status();
        this.is_del = order.getIsDel();
        this.price = order.getTotal_price();
        this.thumbnail = order.getProject().getThumbnail();
        this.description = order.getProject().getDescription();

    }
}
