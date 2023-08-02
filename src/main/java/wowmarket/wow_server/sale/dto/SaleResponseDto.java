package wowmarket.wow_server.sale.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SaleResponseDto {
    private String univ;
    private List<SaleDto> project_list;

    public SaleResponseDto(String univ, List<SaleDto> newDtos) {
        this.univ = univ;
        project_list = newDtos;
    }
}
