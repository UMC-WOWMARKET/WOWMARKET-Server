package wowmarket.wow_server.mypage.myinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.User;

@Getter
@Builder
public class MyInfoResponseDto {
    private String name;
    private String email;
    private String univ;
}
