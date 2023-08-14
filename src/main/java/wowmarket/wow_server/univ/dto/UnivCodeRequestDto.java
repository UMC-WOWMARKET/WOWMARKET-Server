package wowmarket.wow_server.univ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnivCodeRequestDto {
    private String univ_name;
    private String univ_email;
    private int code;
}
