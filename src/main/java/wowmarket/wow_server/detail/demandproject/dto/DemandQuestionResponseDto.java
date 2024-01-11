package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandQuestion;

//참여폼 추가질문 정보 불러오는 Dto

@Getter
public class DemandQuestionResponseDto {
    private Long id;
    private String question; //질문 내용
    private boolean essential; //필수 여부

    public DemandQuestionResponseDto(DemandQuestion demandQuestion) {
        this.id = demandQuestion.getId();
        this.question = demandQuestion.getQuestion();
        this.essential = demandQuestion.isEssential();
    }
}