package wowmarket.wow_server.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.sale.dto.SaleDto;

import java.util.List;

@NoArgsConstructor
@Getter
public class DemandResponseDto {
    private String univ;
    private List<DemandDto> demand_project_list;

    public DemandResponseDto(String univ, List<DemandDto> newDtos) {
        this.univ = univ;
        demand_project_list = newDtos;
    }
}
