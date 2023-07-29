package wowmarket.wow_server.sale.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SaleListResponseDto {
    private String univ;
    private List<SaleResponseDto> project_list;

    public SaleListResponseDto(String univ, List<SaleResponseDto> newDtos) {
        this.univ = univ;
        project_list = newDtos;
    }
}
