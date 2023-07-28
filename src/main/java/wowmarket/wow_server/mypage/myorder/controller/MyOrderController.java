package wowmarket.wow_server.mypage.myorder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormDetailResponseDto;
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
        return myOrderService.findMyOrderFormDetail(user_id, order_id);
    }


}
