package wowmarket.wow_server.mypage.myproject.MySalesOrder.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesOrderDetailResponseDto {
    private Long orderId;
    private String projectName;
    private int totalPrice;
    private String thumbnail;
    private String description;

    private String receiveType;
    private Long deliveryFee;

    private List<MySalesOrderDetailDto> itemList;

    private String receiver;
    private String address;
    private String address_detail;
    private String zipcode;
    private String phone;
    private String message;

    private String sellerBank;
    private String sellerAccount;
    private String sellerAccountName;

    private String depositName;
    private String depositTime;

    private String refundBank;
    private String refundAccount;

    public MySalesOrderDetailResponseDto(List<MySalesOrderDetailDto> newDtos, Orders orders, String address){
        this.orderId = orders.getId();
        this.projectName = orders.getProject().getProjectName();
        this.totalPrice = orders.getTotal_price();
        this.thumbnail = orders.getProject().getThumbnail();
        this.description = orders.getProject().getDescription();

        this.receiveType = orders.getProject().getReceive_type().toString();
        this.deliveryFee = orders.getProject().getDelivery_fee();

        this.itemList = newDtos;

        this.receiver = orders.getReceiver();
        this.address = address;
        this.address_detail = orders.getAddress_detail();
        this.zipcode = orders.getZipcode();
        this.message = orders.getDelivery_msg();
        this.phone = orders.getPhone();

        this.sellerBank = orders.getProject().getBank();
        this.sellerAccount = orders.getProject().getAccount();
        this.sellerAccountName = orders.getProject().getAccount_holder_name();

        this.depositName = orders.getDepositor();
        this.depositTime = orders.getDepositTime();

        this.refundAccount = orders.getAccount();
        this.refundBank = orders.getBank();
    }
}
