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
    private String buyer_bank;
    private String buyer_account;
    private String buyer_account_name;
    private String deposittime;
    private String project_name;
    private String description;
    private int total_price;
    private String thumbnail;
    private int status;
    private int is_del;
    private Long order_id;

    public MyOrderFormDetailResponseDto(List<MyOrderFormDetailDto> itemList, Orders orders, String address){
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
        this.buyer_bank = orders.getBank();
        this.buyer_account = orders.getAccount();
        this.buyer_account_name = orders.getDepositor();
        this.deposittime = orders.getDepositTime();
        this.project_name = orders.getProject().getName();
        this.description = orders.getProject().getDescription();
        this.total_price = orders.getTotal_price();
        this.thumbnail = orders.getProject().getThumbnail();
        this.status = orders.getOrder_status();
        this.is_del = orders.getIsDel();
        this.order_id = orders.getId();
    }

}
