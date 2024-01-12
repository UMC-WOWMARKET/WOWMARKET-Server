package wowmarket.wow_server.admin.userManage.dto;


import lombok.Getter;
import wowmarket.wow_server.domain.User;

@Getter
public class UserManageDto {
    private String email;
    private String name;
    private String univ;

    private UserManageDto(String email, String name, String univ){
        this.email = email;
        this.name = name;
        this.univ = univ;
    }


    public static UserManageDto from(User user){
        UserManageDto userManageDto = new UserManageDto(
                user.getEmail(),
                user.getName(),
                user.getUniv());
        return userManageDto;
    }
}
