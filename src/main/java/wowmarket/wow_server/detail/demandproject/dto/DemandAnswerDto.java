package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;

@Getter
public class DemandAnswerDto {
    private Long questionId; //추가질문 id
    private String answer; //답변 내용
}