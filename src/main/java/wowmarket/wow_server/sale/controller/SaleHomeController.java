package wowmarket.wow_server.sale.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.sale.dto.SaleListResponseDto;
import wowmarket.wow_server.sale.service.SaleHomeService;

@RestController
@RequestMapping("/wowmarket/sale")
@RequiredArgsConstructor
public class SaleHomeController {
    private final SaleHomeService saleHomeService;

    @GetMapping("/{user_id}") //만약 user_id를 넘겨받지 않으면 소속학교를 알 수 없음 근데 url로 받지는 않을 예정이지만 일단 이렇게 구현
//    /wowmarket/sale/{user_id}?univ=${univ}&orderby=${orderby}&lastpostid=${lastpostid}&size=${size}
    public SaleListResponseDto GetSaleProjectListHome(
            @PathVariable Long user_id, //아이디가 없으면???? 로그인 하지 않은 사용자 처리????
            @RequestParam(name = "univ", defaultValue = "myUniv", required = true) String univ, //myUniv, allUniv
            //근데 또 로그인이 안 되어있으면 allUniv가 디폴트..!ㅠㅠㅠㅜㅠ
            @RequestParam(name = "orderby", defaultValue = "endDate", required = true) String orderby, //endDate, popularity, createdDate
            @RequestParam("lastpostid") Long lastpostid, //무한스크롤 구현을 위해 현재 보고 있는 상품의 마지막 id
            @RequestParam("size") int size) { //상품 몇 개씩 보여줄지 size
        //로그인이 되지 않은 상태면 univ = all
        return saleHomeService.findSaleProjectHome(user_id, univ, orderby);
    }
}
