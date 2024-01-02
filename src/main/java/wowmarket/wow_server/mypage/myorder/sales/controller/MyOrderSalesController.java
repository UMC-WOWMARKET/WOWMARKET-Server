package wowmarket.wow_server.mypage.myorder.sales.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myorder.sales.dto.MyOrderFormDetailResponseDto;
import wowmarket.wow_server.mypage.myorder.sales.dto.MyOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.sales.service.MyOrderSalesService;

@RestController
@RequestMapping("/myorder-sales")
@RequiredArgsConstructor
public class MyOrderSalesController {

    private final MyOrderSalesService myOrderService;

    //나의 판매 주문폼 목록 불러오기
    @GetMapping()
    public MyOrderFormListResponseDto getMyOrderList(@RequestParam(value = "page", defaultValue = "1", required = false)int page, @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myOrderService.findAllMyOrderForm(pageable, user);
    }

    //나의 판매 주문폼 상세 보기
    @GetMapping("/detail/{order_id}")
    public MyOrderFormDetailResponseDto getMyDetailOrder(@PathVariable Long order_id){
        return myOrderService.findMyOrderFormDetail(order_id);
    }

    //나의 주문폼 취소하기
    @DeleteMapping("/detail/{order_id}")
    public ResponseEntity deleteMyOrder(@PathVariable Long order_id){
        return myOrderService.deleteMyOrderFormDetail(order_id);
    }

}
