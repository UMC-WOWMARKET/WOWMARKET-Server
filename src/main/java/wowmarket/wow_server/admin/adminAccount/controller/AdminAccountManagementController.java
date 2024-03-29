package wowmarket.wow_server.admin.adminAccount.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.admin.adminAccount.dto.AdminResponseDto;
import wowmarket.wow_server.admin.adminAccount.dto.ChangeRoleRequestDto;
import wowmarket.wow_server.admin.adminAccount.service.AdminAccountManagementService;
import wowmarket.wow_server.domain.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminAccountManagementController {
    private final AdminAccountManagementService adminAccountManagementService;

    @PutMapping("/role/admin")
    public ResponseEntity giveAdminRole(@RequestBody ChangeRoleRequestDto requestDto, @AuthenticationPrincipal User user){
        return adminAccountManagementService.giveAdminRole(requestDto, user);
    }

    @PutMapping("/role/user")
    public ResponseEntity giveUserRole(@RequestBody ChangeRoleRequestDto requestDto, @AuthenticationPrincipal User user){
        return adminAccountManagementService.giveUserRole(requestDto, user);
    }

    @GetMapping("/manage")
    public AdminResponseDto getAdminManagementPage(@AuthenticationPrincipal User user){
        return adminAccountManagementService.findAdmin(user);
    }


}
