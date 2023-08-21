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
    private Long user_id; //현재 로그인된 사용자 id
    private Long seller_id; //프로젝트 판매자 id

    //문의 답변 Dto
    AnswerResponseDto answerResponseDto;


    //사용자가 Null이 아니면서, 문의 답글이 있는 경우
    public QuestionSelectResponseDto(Question question, AnswerResponseDto answerResponseDto, Long user_id, Long seller_id) {
        this.question_id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writer = question.getUser().getName();
        this.secret = question.isSecret();
        this.createdTime = question.getCreated_time();
        //this.lastModifiedTime = question.getLast_modified_time();
        this.answerResponseDto = answerResponseDto;
        this.user_id = user_id;
        this.seller_id = seller_id;
    }

    //사용자가 Null이 아니면서, 문의 답글이 없는 경우
    public QuestionSelectResponseDto(Question question, Long user_id, Long seller_id) {
        this.question_id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writer = question.getUser().getName();
        this.secret = question.isSecret();
        this.createdTime = question.getCreated_time();
        //this.lastModifiedTime = question.getLast_modified_time();
        // this.answerResponseDto = answerResponseDto;
        this.user_id = user_id;
        this.seller_id = seller_id;
    }


    //사용자가 Null이면서, 문의 답글이 있는 경우
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

    //사용자가 Null이면서, 문의 답글이 없는 경우
    public QuestionSelectResponseDto(Question question) {
        this.question_id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writer = question.getUser().getName();
        this.secret = question.isSecret();
        this.createdTime = question.getCreated_time();
        //this.lastModifiedTime = question.getLast_modified_time();
        // this.answerResponseDto = answerResponseDto;
    }
}