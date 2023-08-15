package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderFormRequestDto {
    //직접수령이면 zipcode, address, address_detail 필요 없긴 함.. service에서 구현하나..? 아니면 굳이???
    //[배송정보]
    private String receiver; //수취인명
    private String zipcode; //우편번호
    private String address; //주소
    private String address_detail; //상세주소
    private String phone; //전화번호

    //[입금정보]
    private String depositor; //입금자명
    private LocalDateTime depositTime; //입금시간

    //[환불계좌]
    private String bank; //환불 은행
    private String account; //환불 계좌번호

    //상품명 & 판매가 리스트
    List<OrderRequestDto> orderRequestDtoList;
}