package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;

import java.util.List;

//참여폼 정보 불러오는 Dto

@Getter
public class DemandResponseDto {
    private List<DemandItemResponseDto> demandItemResponseDtoList;
    private List<DemandQuestionResponseDto> demandQuestionResponseDtoList;

    public DemandResponseDto(List<DemandItemResponseDto> demandItemResponseDtoList, List<DemandQuestionResponseDto> demandQuestionResponseDtoList) {
        this.demandItemResponseDtoList = demandItemResponseDtoList;
        this.demandQuestionResponseDtoList = demandQuestionResponseDtoList;
    }
}