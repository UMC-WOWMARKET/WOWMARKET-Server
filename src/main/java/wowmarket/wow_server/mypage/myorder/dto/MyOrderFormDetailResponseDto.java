package wowmarket.wow_server.mypage.myorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MyOrderFormDetailResponseDto {

    private Long order_id;
    private String project_name;
    private String description;
    private int total_price;
    private String thumbnail;
    private int status;
    private int is_del;

    private List<MyOrderFormDetailDto> itemList;
    private String receiver;
    private String address;
    private Long delivery_fee;
    private String zipcode;
    private String message;
    private String phone;

    private String seller_bank;
    private String seller_account;
    private String seller_account_name;

    private String deposit_name;
    private String deposit_time;

    private String refund_bank;
    private String refund_account;


    public MyOrderFormDetailResponseDto(List<MyOrderFormDetailDto> itemList, Orders orders, String address){
        this.order_id = orders.getId();
        this.project_name = orders.getProject().getProjectName();
        this.description = orders.getProject().getDescription();
        this.total_price = orders.getTotal_price();
        this.thumbnail = orders.getProject().getThumbnail();
        this.status = orders.getOrder_status();
        this.is_del = orders.getIsDel();

        this.itemList = itemList;

        this.receiver = orders.getReceiver();
        this.address = address;
        this.delivery_fee = orders.getProject().getDelivery_fee();
        this.zipcode = orders.getZipcode();
        this.message = orders.getDelivery_msg();
        this.phone = orders.getPhone();

        this.seller_bank = orders.getProject().getBank();
        this.seller_account = orders.getProject().getAccount();
        this.seller_account_name = orders.getProject().getAccount_holder_name();

        this.deposit_name = orders.getDepositor();
        this.deposit_time = orders.getDepositTime();

        this.refund_bank = orders.getBank();
        this.refund_account = orders.getAccount();
    }

}
