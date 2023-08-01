package wowmarket.wow_server.mypage.myproject.dto;

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
    private String zipcode;
    private String message;
    private String phone;
    private String bank;
    private String account;
    private String depositor;
    private LocalDateTime deposittime;

    public MySalesOrderDetailResponseDto(List<MySalesOrderDetailDto> newDtos, Orders orders){
        this.itemList = newDtos;
        this.receiver = orders.getReceiver();
        this.address = orders.getAddress();
        this.zipcode = orders.getZipcode();
        this.message = orders.getDelivery_msg();
        this.phone = orders.getPhone();
        this.bank = orders.getBank();
        this.account = orders.getAccount();
        this.depositor = orders.getDepositor();
        this.deposittime = orders.getDepositTime();
    }
}
