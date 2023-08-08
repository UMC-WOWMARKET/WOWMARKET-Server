package wowmarket.wow_server.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDto {
    private Boolean temporaryPw;
    private String grantType;
    private String jwtAccessToken;
    private String jwtRefreshToken;
    private String univ;
}
