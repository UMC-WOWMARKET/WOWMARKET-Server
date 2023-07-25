package wowmarket.wow_server.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Login_Method;
import wowmarket.wow_server.domain.Role;
import wowmarket.wow_server.domain.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoDto {
    private String email;
    private String name;
    private Login_Method login_method;

    @Builder
    public User toEntity(){
        return User.builder()
                .email(email)
                .name(name)
                .login_method(Login_Method.KAKAO)
                .build();
    }
}
