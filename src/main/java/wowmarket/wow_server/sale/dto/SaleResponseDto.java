package wowmarket.wow_server.sale.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SaleResponseDto {
    private String univ;
    private int total_page;
    private int current_page;
    private List<SaleDto> project_list;

    public SaleResponseDto(String user_univ, int total_page, int current_page, List<SaleDto> newDtos) {
        this.univ = user_univ;
        this.total_page = total_page;
        this.current_page = current_page;
        this.project_list = newDtos;
    }
}
