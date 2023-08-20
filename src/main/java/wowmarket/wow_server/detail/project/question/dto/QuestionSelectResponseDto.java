package wowmarket.wow_server.detail.project.question.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.Question;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionSelectResponseDto {
    private Long question_id;        // 게시글 구분을 위한 id 값
    private String title;       // 제목
    private String content;    // 작성 내용
    private String writer;      //작성자
    private boolean secret;     //비밀글 여부
    private LocalDateTime createdTime;        // 게시글 생성 날짜
    //private LocalDateTime lastModifiedTime;;       // 게시글 수정 날짜

    //문의 답변 Dto
    AnswerResponseDto answerResponseDto;

    public QuestionSelectResponseDto(Question question, AnswerResponseDto answerResponseDto) {
        this.question_id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writer = question.getUser().getName();
        this.secret = question.isSecret();
        this.createdTime = question.getCreated_time();
        //this.lastModifiedTime = question.getLast_modified_time();
        this.answerResponseDto = answerResponseDto;
    }
}