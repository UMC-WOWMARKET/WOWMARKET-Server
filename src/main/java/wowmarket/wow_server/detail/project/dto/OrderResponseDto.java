package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.Project;

import java.util.List;

//주문폼 정보 불러오는 Dto

@Getter
public class OrderResponseDto {
    //수령방법
    private String receive_type; //수령방법: [택배배송/직접수령]
    private String receive_address; //직접수령시 픽업장소
    private Long delivery_fee; //택배배송시 배송비

    //판매자 계좌 정보
    private String bank; //입금계좌: 은행
    private String account; //입금계좌: 계좌
    private String account_holder_name; //예금주

    private List<ItemResponseDto> itemResponseDtoList;

    public OrderResponseDto(Project project, List<ItemResponseDto> itemResponseDtoList) {
        this.receive_type=project.getReceive_type();
        this.receive_address=project.getReceive_address();
        this.delivery_fee=project.getDelivery_fee();
        this.bank=project.getBank();
        this.account=project.getAccount();
        this.account_holder_name = project.getAccount_holder_name();
        this.itemResponseDtoList = itemResponseDtoList;
    }
}