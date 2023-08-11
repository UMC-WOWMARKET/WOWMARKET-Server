package wowmarket.wow_server.sale.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/home")
//    /wowmarket/sale/home?pageNo=${pageNo}&orderBy=${orderBy}&univ=${univ}
    public SaleResponseDto GetSaleProjectPageHome(
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @RequestParam(name = "orderBy", defaultValue = "endDate", required = true) String orderBy,
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ) {
        System.out.println("\n[GetSaleProjectListHome Controller] 판매 홈 페이지 로직\n");
        Sort sort;
        if (orderBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "view");
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return saleHomeService.findProjectHome(pageable, univ);
    }

    @GetMapping
//     /wowmarket/sale?search={search}&pageNo=${pageNo}&orderBy=${orderBy}&univ=${univ}
    public SaleResponseDto GetSaleProjectListSearch(
            @RequestParam("search") String search,
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @RequestParam(name = "orderBy", defaultValue = "endDate", required = true) String orderBy,
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ) {
        System.out.println("\n[GetSaleProjectListSearch Controller] 판매 검색 페이지 로직\n");
        Sort sort;
        if (orderBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "view");
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return saleSearchService.findProjectHome(search, pageable, univ);
    }
}