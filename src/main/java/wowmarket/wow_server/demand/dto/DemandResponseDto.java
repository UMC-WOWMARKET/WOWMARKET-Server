package wowmarket.wow_server.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.sale.dto.SaleDto;

import java.util.List;

@NoArgsConstructor
@Getter
public class DemandResponseDto {
    private String univ;
    private int total_page;
    private int current_page;
    private List<DemandDto> project_list;

    public DemandResponseDto(String univ, int total_page, int current_page, List<DemandDto> newDtos) {
        this.univ = univ;
        this.total_page = total_page;
        this.current_page = current_page;
        this.project_list = newDtos;
    }
}
