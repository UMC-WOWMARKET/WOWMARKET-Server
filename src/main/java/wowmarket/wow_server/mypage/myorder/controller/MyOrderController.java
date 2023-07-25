package wowmarket.wow_server.mypage.myorder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.service.MyOrderService;

@RestController
@RequestMapping("/wowmarket/mypage")
@RequiredArgsConstructor
public class MyOrderController {

    private final MyOrderService myOrderService;

    @GetMapping("/{user_id}/myorder")
    public MyOrderFormListResponseDto getMyOrderList(@PathVariable Long user_id, @RequestParam(value = "page", defaultValue = "1")int page){
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("order_id").descending());
        return myOrderService.findAllMyOrderForm(pageable);
    }

}
