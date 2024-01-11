package wowmarket.wow_server.register.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandItem;
import wowmarket.wow_server.domain.DemandQuestion;
import wowmarket.wow_server.domain.OrderQuestion;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterQuestionDto {

    private String question;
    private boolean essential;

    @Builder
    public OrderQuestion toOrderQuestion(){
        return OrderQuestion
                .builder()
                .question(question)
                .essential(essential)
                .build();
    }

    @Builder
    public DemandQuestion toDemandQuestion(){
        return DemandQuestion
                .builder()
                .question(question)
                .essential(essential)
                .build();
    }
}
