package wowmarket.wow_server.sale.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.sale.dto.SaleListResponseDto;
import wowmarket.wow_server.sale.service.SaleHomeService;
import wowmarket.wow_server.sale.service.SaleSearchService;

@RestController
@RequestMapping("/wowmarket/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleHomeService saleHomeService;
    private final SaleSearchService saleSearchService;

    @GetMapping("/home")
//    /wowmarket/sale?univ=${univ}&orderby=${orderby}&lastpostid=${lastpostid}&size=${size}
    public SaleListResponseDto GetSaleProjectListHome(
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ, //myUniv, allUniv
            //근데 또 로그인이 안 되어있으면 allUniv가 디폴트..!ㅠㅠㅠㅜㅠ
            @RequestParam(name = "orderby", defaultValue = "endDate", required = true) String orderby, //endDate, popularity, createdDate
            @RequestParam("lastpostid") Long lastpostid, //무한스크롤 구현을 위해 현재 보고 있는 상품의 마지막 id
            @RequestParam("size") int size) { //상품 몇 개씩 보여줄지 size
        //로그인이 되지 않은 상태면 univ = all
        return saleHomeService.findSaleProjectHome(univ, orderby);
    }

    @GetMapping
    public SaleListResponseDto GetSaleProjectListSearch(
            @RequestParam("search") String search
    ) {
        return saleSearchService.findProjectBySearch(search);//로그인 유무와 유저 정보 필요!!
    }
}

//로그인 && 학교 인증 -> 소속학교 + 마감 임박 순
//로그인 X || 학교 인증 X -> 전체 학교 + 마감 입박 순
