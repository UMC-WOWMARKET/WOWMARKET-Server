package wowmarket.wow_server.sale.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.sale.dto.SaleListResponseDto;
import wowmarket.wow_server.sale.service.SaleSearchService;

@RestController
@RequestMapping("/wowmarket/sale")
@RequiredArgsConstructor
public class SaleSearchController {
    private final SaleSearchService saleSearchService;

    @GetMapping
    public SaleListResponseDto GetSaleProjectListSearch(
            @RequestParam("search") String search
    ) {
        return saleSearchService.findProjectBySearch(search);//로그인 유무와 유저 정보 필요!!
    }
}
