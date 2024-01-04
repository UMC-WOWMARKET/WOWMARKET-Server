package wowmarket.wow_server.sale.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SaleResponseDto {
    private String userUniv;
    private int totalPage;
    private int currentPage;
    private List<SaleDto> projectList;

    public SaleResponseDto(String userUniv, int totalPage, int currentPage, List<SaleDto> newDtos) {
        this.userUniv = userUniv;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.projectList = newDtos;
    }
}
