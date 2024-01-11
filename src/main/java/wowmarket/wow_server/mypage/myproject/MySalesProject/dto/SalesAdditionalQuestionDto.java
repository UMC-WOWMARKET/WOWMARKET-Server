package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.OrderQuestion;

@Getter
public class SalesAdditionalQuestionDto {
    private String question;
    private boolean essential;

    public SalesAdditionalQuestionDto(OrderQuestion orderQuestion){
        this.question = orderQuestion.getQuestion();
        this.essential = orderQuestion.isEssential();
    }
}
