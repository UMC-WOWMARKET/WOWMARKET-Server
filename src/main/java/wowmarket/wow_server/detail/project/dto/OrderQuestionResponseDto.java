package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.OrderQuestion;

//주문폼 추가질문 정보 불러오는 Dto

@Getter
public class OrderQuestionResponseDto {
    private Long id;
    private String question; //질문 내용
    private boolean essential; //필수 여부

    public OrderQuestionResponseDto(OrderQuestion orderQuestion) {
        this.id = orderQuestion.getId();
        this.question = orderQuestion.getQuestion();
        this.essential = orderQuestion.isEssential();
    }
}