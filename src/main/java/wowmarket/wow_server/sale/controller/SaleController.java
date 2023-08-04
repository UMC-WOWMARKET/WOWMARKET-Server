package wowmarket.wow_server.sale.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.sale.dto.SaleResponseDto;
import wowmarket.wow_server.sale.service.SaleHomeService;
import wowmarket.wow_server.sale.service.SaleSearchService;

@RestController
@RequestMapping("/wowmarket/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleHomeService saleHomeService;
    private final SaleSearchService saleSearchService;

    //로그인 O && 학교인증 O -> 소속학교 + 마감임박순
    //로그인 X || 학교인증 X -> 전체 학교 + 마감임박순
    @GetMapping("/home")
//    /wowmarket/sale/home?univ=${univ}&orderby=${orderby}&lastpostid=${lastpostid}&size=${size}
    public SaleResponseDto GetSaleProjectListHome(
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ, //myUniv, allUniv
            @RequestParam(name = "orderby", defaultValue = "endDate", required = true) String orderby, //endDate, popularity, createdDate
            @RequestParam(name = "lastpostid") Long lastpostid, //무한스크롤 구현을 위해 현재 보고 있는 상품의 마지막 id
            @RequestParam(name = "size", defaultValue = "9", required = true) int size) { //상품 몇 개씩 보여줄지 size
        System.out.println("\n[GetSaleProjectListHome Controller] 판매 홈 페이지 로직\n");
        return saleHomeService.findProjectHome(univ, orderby, lastpostid, size);
    }


    @GetMapping
//     /wowmarket/sale?search={search}&univ=${univ}&orderby=${orderby}&lastpostid=${lastpostid}&size=${size}
    public SaleResponseDto GetSaleProjectListSearch(
            @RequestParam("search") String search,
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ, //myUniv, allUniv
            @RequestParam(name = "orderby", defaultValue = "endDate", required = true) String orderby, //endDate, popularity, createdDate
            @RequestParam("lastpostid") Long lastpostid, //무한스크롤 구현을 위해 현재 보고 있는 상품의 마지막 id
            @RequestParam(name = "size", defaultValue = "9", required = true) int size) {
        System.out.println("\n[GetSaleProjectListSearch Controller] 판매 검색 페이지 로직\n");
        return saleSearchService.findProjectSearch(search, univ, orderby);//로그인 유무와 유저 정보 필요!!
    }
}

