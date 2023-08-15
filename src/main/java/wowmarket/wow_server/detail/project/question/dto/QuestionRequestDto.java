package wowmarket.wow_server.detail.project.question.dto;

import lombok.Getter;


@Getter
public class QuestionRequestDto {
    private String title;        // 제목
    private String content;        // 작성 내용
    private boolean secret;      //비밀글 여부
}