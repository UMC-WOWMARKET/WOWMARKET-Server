package wowmarket.wow_server.mypage.myproject.MySalesOrder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesOrderDetailResponseDto {
    private List<MySalesOrderDetailDto> itemList;
    private String receiver;
    private String address;
    private String address_detail;
    private String zipcode;
    private String message;
    private String phone;
    private String seller_bank;
    private String seller_account;
    private String seller_account_name;
    private String buyer_bank;
    private String buyer_account;
    private String buyer_account_name;
    private LocalDateTime deposittime;

    public MySalesOrderDetailResponseDto(List<MySalesOrderDetailDto> newDtos, Orders orders){
        this.itemList = newDtos;
        this.receiver = orders.getReceiver();
        this.address = orders.getAddress();
        this.address_detail = orders.getAddress_detail();
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
    }
}
