package wowmarket.wow_server.mypage.myorder.sales.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Orders;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyOrderSalesDetailResponseDto {

    private Long orderId;
    private String projectName;
    private String description;
    private int totalPrice;
    private String thumbnail;
    private int status;
    private int isDel;

    private List<MyOrderSalesDetailItemDto> itemList;
    private String receiver;
    private String address;
    private Long deliveryFee;
    private String zipcode;
    private String message;
    private String phone;

    private String sellerBank;
    private String sellerAccount;
    private String sellerAccountName;

    private String depositName;
    private String depositTime;

    private String refundBank;
    private String refundAccount;


    public MyOrderSalesDetailResponseDto(List<MyOrderSalesDetailItemDto> itemList, Orders orders, String address){
        this.orderId = orders.getId();
        this.projectName = orders.getProject().getProjectName();
        this.description = orders.getProject().getDescription();
        this.totalPrice = orders.getTotal_price();
        this.thumbnail = orders.getProject().getThumbnail();
        this.status = orders.getOrder_status();
        this.isDel = orders.getIsDel();

        this.itemList = itemList;

        this.receiver = orders.getReceiver();
        this.address = address;
        this.deliveryFee = orders.getProject().getDelivery_fee();
        this.zipcode = orders.getZipcode();
        this.message = orders.getDelivery_msg();
        this.phone = orders.getPhone();

        this.sellerBank = orders.getProject().getBank();
        this.sellerAccount = orders.getProject().getAccount();
        this.sellerAccountName = orders.getProject().getAccount_holder_name();

        this.depositName = orders.getDepositor();
        this.depositTime = orders.getDepositTime();

        this.refundBank = orders.getBank();
        this.refundAccount = orders.getAccount();
    }

}
