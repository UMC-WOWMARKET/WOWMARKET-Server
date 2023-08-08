package wowmarket.wow_server.mypage.myorder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormDetailResponseDto;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.service.MyOrderService;

@RestController
@RequestMapping("/wowmarket/mypage")
@RequiredArgsConstructor
public class MyOrderController {

    private final MyOrderService myOrderService;

    @GetMapping("/myorder")
    public MyOrderFormListResponseDto getMyOrderList(@RequestParam(value = "page", defaultValue = "1", required = false)int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myOrderService.findAllMyOrderForm(pageable);
    }

    @GetMapping("/{user_id}/myorder/detail/{order_id}")
    public MyOrderFormDetailResponseDto getMyDetailOrder(@PathVariable Long user_id, @PathVariable Long order_id){
        return myOrderService.findMyOrderFormDetail(order_id);
    }

    @DeleteMapping("/{user_id}/myorder/detail/{order_id}")
    public ResponseEntity deleteMyOrder(@PathVariable Long user_id, @PathVariable Long order_id){
        myOrderService.deleteMyOrderFormDetail(order_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
