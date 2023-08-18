package wowmarket.wow_server.detail.project.question.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.Answer;

import java.time.LocalDateTime;

@Getter
public class AnswerResponseDto {
    private Long answer_id;        // 게시글 구분을 위한 id 값
    private String title;       // 제목
    private String writer;      //작성자
    private String content;    // 작성 내용
    private LocalDateTime createdTime;        // 게시글 생성 날짜
    //private LocalDateTime lastModifiedTime;;       // 게시글 수정 날짜

    public AnswerResponseDto(Answer answer) {
        this.answer_id = answer.getId();
        this.title = answer.getTitle();
        this.writer = answer.getUser().getName();
        this.content = answer.getContent();
        this.createdTime = answer.getCreated_time();
        //this.lastModifiedTime = notice.getLast_modified_time();
    }
}