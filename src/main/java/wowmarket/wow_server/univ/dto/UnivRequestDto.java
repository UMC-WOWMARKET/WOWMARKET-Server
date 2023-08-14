package wowmarket.wow_server.univ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UnivRequestDto {
    private String univ_name;
    private String univ_email;
}
