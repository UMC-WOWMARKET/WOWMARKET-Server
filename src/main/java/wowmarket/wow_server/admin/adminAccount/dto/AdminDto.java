package wowmarket.wow_server.admin.adminAccount.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.User;

@Getter
@NoArgsConstructor
public class AdminDto {
    private String email;
    private String userName;

    public AdminDto(User user){
        this.email = user.getEmail();
        this.userName = user.getName();
    }
}
