package wowmarket.wow_server.admin.userManage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.admin.userManage.dto.UserManageDto;
import wowmarket.wow_server.admin.userManage.dto.UserSearchCond;
import wowmarket.wow_server.admin.userManage.service.UserManageService;
import wowmarket.wow_server.domain.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserManageController {
    private final UserManageService userManageService;

    @GetMapping("/manage/user")
    public List<UserManageDto> getUserList(UserSearchCond cond, @AuthenticationPrincipal User user){
        return userManageService.getUserList(cond, user);
    }

    @PutMapping("/restrict/user")
    public ResponseEntity deleteUser(String email, @AuthenticationPrincipal User user){
        userManageService.deleteUser(email, user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
