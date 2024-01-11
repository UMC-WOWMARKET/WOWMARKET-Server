package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;

@Getter
public class OrderAnswerDto {
    private Long questionId; //추가질문 id
    private String answer; //답변 내용
}