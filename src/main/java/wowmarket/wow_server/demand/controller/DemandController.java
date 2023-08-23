package wowmarket.wow_server.demand.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.demand.dto.DemandResponseDto;
import wowmarket.wow_server.demand.service.DemandHomeService;
import wowmarket.wow_server.demand.service.DemandSearchService;
import wowmarket.wow_server.domain.User;

@RestController
@RequestMapping("/demand")
@RequiredArgsConstructor
public class DemandController {
    private final DemandHomeService demandHomeService;
    private final DemandSearchService demandSearchService;

    @GetMapping("/home")
//    /demand/home?pageNo=${pageNo}&orderBy=${orderBy}&univ=${univ}
    public DemandResponseDto GetDemandProjectPageHome(
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @RequestParam(name = "orderBy", defaultValue = "endDate", required = true) String orderBy,
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ,
            @AuthenticationPrincipal User user) {
        System.out.println("\n[GetSaleProjectListHome Controller] 수요조사 홈 페이지 로직\n");
        Sort sort;
        if (orderBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "view");
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return demandHomeService.findDemandProjectHome(pageable, univ, user);
    }

    @GetMapping
//    /demand?search={search}&pageNo=${pageNo}&orderBy=${orderBy}&univ=${univ}
    public DemandResponseDto GetDemandProjectListSearch(
            @RequestParam("search") String search,
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @RequestParam(name = "orderBy", defaultValue = "endDate", required = true) String orderBy,
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ,
            @AuthenticationPrincipal User user) {
        System.out.println("\n[GetDemandProjectListSearch Controller] 수요조사 검색 페이지 로직\n");
        Sort sort;
        if (orderBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "view");
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return demandSearchService.findDemandProjectSearch(search, pageable, univ, user);
    }
}
