package wowmarket.wow_server.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wowmarket.wow_server.domain.Role;
import wowmarket.wow_server.domain.User;

@Data
@Builder
@AllArgsConstructor
public class UserSignUpRequestDto {
    @NotBlank(message = "아이디를 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
//            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;
    private String checkedPassword;
    private String name;

    private Role role;

    @Builder
    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(Role.ROLE_USER)
                .build();
    }
}
