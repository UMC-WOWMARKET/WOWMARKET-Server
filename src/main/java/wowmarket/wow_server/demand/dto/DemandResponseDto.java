package wowmarket.wow_server.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class DemandResponseDto {
    private String userUniv;
    private int totalPage;
    private int currentPage;
    private List<DemandDto> projectList;

    public DemandResponseDto(String univ, int totalPage, int currentPage, List<DemandDto> newDtos) {
        this.userUniv = univ;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.projectList = newDtos;
    }
}
