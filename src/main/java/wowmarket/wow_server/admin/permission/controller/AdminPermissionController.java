package wowmarket.wow_server.admin.permission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.admin.permission.dto.request.PermissionProjectsRequest;
import wowmarket.wow_server.admin.permission.dto.response.ProjectListResponse;
import wowmarket.wow_server.admin.permission.service.AdminPermissionService;

@RestController
@RequestMapping("/admin/permission")
@RequiredArgsConstructor
public class AdminPermissionController {
    private final AdminPermissionService adminPermissionService;

    @GetMapping("/project")
    public ProjectListResponse getProjectList(@RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo) {
        Sort sort = Sort.by(
                Sort.Order.desc("permission"),
                Sort.Order.asc("created_time")
        );
        PageRequest pageable = PageRequest.of(pageNo - 1, 9, sort);
        return adminPermissionService.getProjectList(pageNo, pageable);
    }

    @PostMapping("/project")
    public ResponseEntity<Void> updateProjectPermission(
            @RequestParam(name = "permission", defaultValue = "APPROVED", required = true) String permission,
            @RequestBody PermissionProjectsRequest permissionProjectsRequest) {
        return adminPermissionService.updateProjectPermission(permission, permissionProjectsRequest);
    }

    @GetMapping("/demand")
    public ProjectListResponse getDemandProjectList(@RequestParam(name = "pageNo", defaultValue = "1", required = true) int pageNo) {
        Sort sort = Sort.by(
                Sort.Order.desc("permission"),
                Sort.Order.asc("created_time")
        );
        PageRequest pageable = PageRequest.of(pageNo - 1, 9, sort);
        return adminPermissionService.getDemandProjectList(pageNo, pageable);
    }

    @PostMapping("/demand")
    public ResponseEntity<Void> updateDemandProjectPermission(
            @RequestParam(name = "permission", defaultValue = "APPROVED", required = true) String permission,
            @RequestBody PermissionProjectsRequest permissionProjectsRequest) {
        return adminPermissionService.updateDemandProjectPermission(permission, permissionProjectsRequest);
    }
}
