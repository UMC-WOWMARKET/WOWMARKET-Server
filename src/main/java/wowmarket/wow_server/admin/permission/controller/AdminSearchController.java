package wowmarket.wow_server.admin.permission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.admin.permission.dto.response.ProjectListResponse;
import wowmarket.wow_server.admin.permission.service.AdminSearchService;

@RestController
@RequestMapping("/admin/permission")
@RequiredArgsConstructor
public class AdminSearchController {
    private final AdminSearchService adminSearchService;

    // 구분: 판매 &  검색 항목: 프로젝트 이름 or 소속학교
    @GetMapping("/sale") //구분: 판매
//     /?search={search}&pageNo=${pageNo}&orderBy=${orderBy}&univ=${univ}
    public ProjectListResponse GetAdminProjectListSearch(
            @RequestParam("search") String search,
            @RequestParam("univ") String univ,
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @RequestParam(name = "orderBy", defaultValue = "endDate", required = true) String orderBy) {
        Sort sort;
        if (orderBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "view");
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return adminSearchService.findProjectAdmin(search, pageable, univ);
    }

    // 구분: 수요조사 &  검색 항목: 프로젝트 이름 or 소속학교
    @GetMapping("/demandSale") //구분: 수요조사
//     /?search={search}&pageNo=${pageNo}&orderBy=${orderBy}&univ=${univ}
    public ProjectListResponse GetAdminDemandListSearch(
            @RequestParam("search") String search,
            @RequestParam("univ") String univ,
            @RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo,
            @RequestParam(name = "orderBy", defaultValue = "endDate", required = true) String orderBy) {
        Sort sort;
        if (orderBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "view");
        } else {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 9, sort);
        return adminSearchService.findDemandAdmin(search, pageable, univ);
    }

}
