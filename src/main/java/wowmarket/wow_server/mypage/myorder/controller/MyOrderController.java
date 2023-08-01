package wowmarket.wow_server.mypage.myorder.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormDetailResponseDto;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormDetailUpdateRequestDto;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.service.MyOrderService;

@RestController
@RequestMapping("/wowmarket/mypage")
@RequiredArgsConstructor
public class MyOrderController {

    private final MyOrderService myOrderService;

    @GetMapping("/{user_id}/myorder")
    public MyOrderFormListResponseDto getMyOrderList(@PathVariable Long user_id, @RequestParam(value = "page", defaultValue = "1", required = false)int page){
        //Pageable pageable = PageRequest.of(page - 1, 10 /*, Sort.by("id").descending()*/);
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myOrderService.findAllMyOrderForm(user_id, pageable);
    }

    @GetMapping("/{user_id}/myorder/detail/{order_id}")
    public MyOrderFormDetailResponseDto getMyDetailOrder(@PathVariable Long user_id, @PathVariable Long order_id){
        return myOrderService.findMyOrderFormDetail(order_id);
    }
    @GetMapping("/{user_id}/myorder/detail/{order_id}/update")
    public MyOrderFormDetailResponseDto updateMyDetailOrder(@PathVariable Long user_id, @PathVariable Long order_id){
        return myOrderService.findMyOrderFormDetail(order_id);
    }

    @PutMapping("/{user_id}/myorder/detail/{order_id}/save")
    public ResponseEntity saveMyDetailOrder(@PathVariable Long user_id, @PathVariable Long order_id, @RequestBody MyOrderFormDetailUpdateRequestDto requestDto){
        myOrderService.updateMyOrderFormDetail(order_id, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
