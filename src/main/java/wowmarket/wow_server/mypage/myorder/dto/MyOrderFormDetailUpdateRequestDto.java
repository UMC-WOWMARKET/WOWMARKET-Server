package wowmarket.wow_server.mypage.myorder.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MyOrderFormDetailUpdateRequestDto {
    List<MyOrderFormItemListRequestDto> itemList;
    private int totalprice;
    private String receiver;
    private String address;
    private String zipcode;
    private String message;
    private String phone;
    private String bank;
    private String account;
    private String depositor;
    private LocalDateTime deposittime;
}
