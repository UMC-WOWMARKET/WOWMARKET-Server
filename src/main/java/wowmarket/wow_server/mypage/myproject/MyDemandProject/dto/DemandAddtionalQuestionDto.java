package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandQuestion;
import wowmarket.wow_server.domain.OrderQuestion;

@Getter
public class DemandAddtionalQuestionDto {
    private String question;
    private boolean essential;

    public DemandAddtionalQuestionDto(DemandQuestion demandQuestion){
        this.question = demandQuestion.getQuestion();
        this.essential = demandQuestion.isEssential();
    }
}
