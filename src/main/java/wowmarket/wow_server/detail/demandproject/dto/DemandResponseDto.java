package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandProject;

import java.util.List;

//참여폼 정보 불러오는 Dto

@Getter
public class DemandResponseDto {
    private boolean sellToAll;
    private List<DemandItemResponseDto> demandItemResponseDtoList;
    private List<DemandQuestionResponseDto> demandQuestionResponseDtoList;

    public DemandResponseDto(DemandProject demandProject, List<DemandItemResponseDto> demandItemResponseDtoList, List<DemandQuestionResponseDto> demandQuestionResponseDtoList) {
        this.sellToAll = demandProject.isSellToAll();
        this.demandItemResponseDtoList = demandItemResponseDtoList;
        this.demandQuestionResponseDtoList = demandQuestionResponseDtoList;
    }
}