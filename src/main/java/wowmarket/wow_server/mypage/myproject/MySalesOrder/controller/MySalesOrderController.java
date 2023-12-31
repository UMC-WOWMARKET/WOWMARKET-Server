package wowmarket.wow_server.mypage.myproject.MySalesOrder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.dto.MySalesOrderDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.dto.MySalesOrderListResponseDto;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.dto.MySalesOrderStatusRequestDto;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.service.MySalesOrderService;

@RestController
@RequestMapping("/myproject/order")
@RequiredArgsConstructor
public class MySalesOrderController {

    private final MySalesOrderService mySalesOrderService;
    //수집폼 리스트
    @GetMapping
    public MySalesOrderListResponseDto getMySalesOrderForms(@RequestParam(value = "page", defaultValue = "1", required = false)int page, @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return mySalesOrderService.findMySalesOrderForms(pageable, user);
    }

    //수집폼 상태 변경
    @PutMapping("/{order_id}")
    public ResponseEntity updateMySalesOrderFormStatus(@PathVariable Long order_id, @RequestBody MySalesOrderStatusRequestDto requestDto, @AuthenticationPrincipal User user){
        mySalesOrderService.updateMySalesOrderStatus(order_id, requestDto, user);
        return new ResponseEntity(HttpStatus.OK);
    }

    //판매 수집폼 상세보기
    @GetMapping("/detail/{order_id}")
    public MySalesOrderDetailResponseDto getMySalesOrderDetail(@PathVariable Long order_id, @AuthenticationPrincipal User user){
        return mySalesOrderService.findMySalesOrderDetail(order_id, user);
    }

    @DeleteMapping("/detail/{order_id}")
    public ResponseEntity deleteMySalesOrderForm(@PathVariable Long order_id, @AuthenticationPrincipal User user){
        mySalesOrderService.deleteMySalesOrder(order_id, user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
