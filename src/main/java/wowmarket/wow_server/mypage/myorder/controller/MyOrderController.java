package wowmarket.wow_server.mypage.myorder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormDetailResponseDto;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.service.MyOrderService;

@RestController
@RequestMapping("/mypage/myorder")
@RequiredArgsConstructor
public class MyOrderController {

    private final MyOrderService myOrderService;

    //나의 주문폼 목록 불러오기
    @GetMapping()
    public MyOrderFormListResponseDto getMyOrderList(@RequestParam(value = "page", defaultValue = "1", required = false)int page, @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myOrderService.findAllMyOrderForm(pageable, user);
    }

    //나의 주문폼 상세 보기
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
