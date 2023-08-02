package wowmarket.wow_server.demand.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.demand.dto.DemandResponseDto;
import wowmarket.wow_server.demand.service.DemandHomeService;
import wowmarket.wow_server.demand.service.DemandSearchService;

@RestController
@RequestMapping("/wowmarket/demand")
@RequiredArgsConstructor
public class DemandController {
    private final DemandHomeService demandHomeService;
    private final DemandSearchService demandSearchService;

    //로그인 O && 학교인증 O -> 소속학교 + 마감임박순
    //로그인 X || 학교인증 X -> 전체 학교 + 마감임박순
    @GetMapping("/home")
//    /wowmarket/demand/home?univ=${univ}&orderby=${orderby}&lastpostid=${lastpostid}&size=${size}
    public DemandResponseDto GetDemandProjectListHome(
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ, //myUniv, allUniv
            @RequestParam(name = "orderby", defaultValue = "endDate", required = true) String orderby, //endDate, popularity, createdDate
            @RequestParam("lastpostid") Long lastpostid, //무한스크롤 구현을 위해 현재 보고 있는 상품의 마지막 id
            @RequestParam("size") int size) {
        System.out.println("\n[GetDemandProjectListHome Controller] 수요조사 홈 페이지 로직\n");
        return demandHomeService.findDemandProjectHome(univ, orderby);
    }

    @GetMapping
//    /wowmarket/demand?search={search}&univ=${univ}&orderby=${orderby}&lastpostid=${lastpostid}&size=${size}
    public DemandResponseDto GetDemandProjectListSearch(
            @RequestParam("search") String search,
            @RequestParam(name = "univ", defaultValue = "allUniv", required = true) String univ, //myUniv, allUniv
            @RequestParam(name = "orderby", defaultValue = "endDate", required = true) String orderby, //endDate, popularity, createdDate
            @RequestParam("lastpostid") Long lastpostid, //무한스크롤 구현을 위해 현재 보고 있는 상품의 마지막 id
            @RequestParam("size") int size) {
        System.out.println("\n[GetDemandProjectListSearch Controller] 수요조사 검색 페이지 로직\n");
        return demandSearchService.findDemandProjectSearch(search, univ, orderby);
    }
}
