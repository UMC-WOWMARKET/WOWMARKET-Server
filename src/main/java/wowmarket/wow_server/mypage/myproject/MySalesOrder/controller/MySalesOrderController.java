package wowmarket.wow_server.mypage.myproject.MySalesOrder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.dto.MySalesOrderDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.dto.MySalesOrderListResponseDto;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.dto.MySalesOrderStatusRequestDto;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.service.MySalesOrderService;

@RestController
@RequestMapping("/wowmarket/mypage/myproject/order")
@RequiredArgsConstructor
public class MySalesOrderController {

    private final MySalesOrderService mySalesOrderService;
    @GetMapping
    public MySalesOrderListResponseDto getMySalesOrderForms(@RequestParam(value = "page", defaultValue = "1", required = false)int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return mySalesOrderService.findMySalesOrderForms(pageable);
    }

    @PutMapping("/{order_id}")
    public ResponseEntity updateMySalesOrderFormStatus(@PathVariable Long order_id, @RequestBody MySalesOrderStatusRequestDto requestDto){
        mySalesOrderService.updateMySalesOrderStatus(order_id, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/detail/{order_id}")
    public MySalesOrderDetailResponseDto getMySalesOrderDetail(@PathVariable Long order_id){
        return mySalesOrderService.findMySalesOrderDetail(order_id);
    }

    @DeleteMapping("/detail/{order_id}")
    public ResponseEntity deleteMySalesOrderForm(@PathVariable Long order_id){
        mySalesOrderService.deleteMySalesOrder(order_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
