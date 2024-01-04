package wowmarket.wow_server.mypage.myorder.demand.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myorder.demand.dto.MyDemandOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.demand.service.MyOrderDemandService;

@RestController
@RequestMapping("/myorder-demand")
@RequiredArgsConstructor
public class MyOrderDemandController {
    private final MyOrderDemandService myOrderDemandService;

    //나의 수요조사 주문폼 목록 불러오기
    @GetMapping()
    public MyDemandOrderFormListResponseDto getMyOrderDemandList(@RequestParam(value = "page", defaultValue = "1", required = false)int page, @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myOrderDemandService.findMyAllDemandOrderForm(pageable, user);
    }


}
