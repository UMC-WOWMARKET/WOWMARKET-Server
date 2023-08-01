package wowmarket.wow_server.mypage.myorder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MyOrderFormDetailResponseDto {

    private List<MyOrderFormDetailDto> itemList;
    private String receiver;
    private String address;
    private String zipcode;
    private String message;
    private String phone;
    private String bank;
    private String account;
    private String depositor;
    private LocalDateTime deposittime;

    public MyOrderFormDetailResponseDto(List<MyOrderFormDetailDto> itemList, String receiver, String address, String zipcode, String message, String phone, String bank, String account, String depositor, LocalDateTime deposittime){
        this.itemList = itemList;
        this.receiver = receiver;
        this.address = address;
        this.zipcode = zipcode;
        this.message = message;
        this.phone = phone;
        this.bank = bank;
        this.account = account;
        this.depositor = depositor;
        this.deposittime = deposittime;
    }

}
