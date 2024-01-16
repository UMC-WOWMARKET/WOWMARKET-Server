package wowmarket.wow_server.detail.demandproject.dto;


import lombok.Getter;
import java.util.List;

@Getter
public class DemandFormRequestDto {
    //상품명 & 판매가 리스트
    List<DemandDetailRequestDto> demandDetailRequestDtoList;

    //추가질문 답변 리스트
    List<DemandAnswerDto> demandAnswerDtoList;
}

