package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.OrderQuestion;

@Getter
public class AdditionalQuestionDto {
    private String question;
    private boolean essential;

    public AdditionalQuestionDto(OrderQuestion orderQuestion){
        this.question = orderQuestion.getQuestion();
        this.essential = orderQuestion.isEssential();
    }
}
