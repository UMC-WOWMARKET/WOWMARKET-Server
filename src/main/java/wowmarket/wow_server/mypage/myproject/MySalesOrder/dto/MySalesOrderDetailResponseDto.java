package wowmarket.wow_server.mypage.myproject.MySalesOrder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesOrderDetailResponseDto {
    private Long order_id;
    private String project_name;
    private int total_price;
    private String thumbnail;
    private String description;

    private String receive_type;
    private Long delivery_fee;

    private List<MySalesOrderDetailDto> itemList;

    private String receiver;
    private String address;
    private String address_detail;
    private String zipcode;
    private String phone;
    private String message;

    private String seller_bank;
    private String seller_account;
    private String seller_account_name;

    private String deposit_name;
    private String deposit_time;

    private String refund_bank;
    private String refund_account;

    public MySalesOrderDetailResponseDto(List<MySalesOrderDetailDto> newDtos, Orders orders, String address){
        this.order_id = orders.getId();
        this.project_name = orders.getProject().getProjectName();
        this.total_price = orders.getTotal_price();
        this.thumbnail = orders.getProject().getThumbnail();
        this.description = orders.getProject().getDescription();

        this.receive_type = orders.getProject().getReceive_type().toString();
        this.delivery_fee = orders.getProject().getDelivery_fee();

        this.itemList = newDtos;

        this.receiver = orders.getReceiver();
        this.address = address;
        this.address_detail = orders.getAddress_detail();
        this.zipcode = orders.getZipcode();
        this.message = orders.getDelivery_msg();
        this.phone = orders.getPhone();

        this.seller_bank = orders.getProject().getBank();
        this.seller_account = orders.getProject().getAccount();
        this.seller_account_name = orders.getProject().getAccount_holder_name();

        this.deposit_name = orders.getDepositor();
        this.deposit_time = orders.getDepositTime();

        this.refund_account = orders.getAccount();
        this.refund_bank = orders.getBank();
    }
}
