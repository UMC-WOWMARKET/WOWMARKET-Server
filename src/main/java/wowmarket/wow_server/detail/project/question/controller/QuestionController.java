package wowmarket.wow_server.detail.project.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.detail.project.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.project.question.dto.AnswerRequestDto;
import wowmarket.wow_server.detail.project.question.dto.AnswerResponseDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionRequestDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionResponseDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionSelectResponseDto;
import wowmarket.wow_server.detail.project.question.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    // 문의 작성 (누구나)
    @PostMapping("/{project_id}/question")
    public QuestionResponseDto createQuestion(@PathVariable Long project_id, @RequestBody QuestionRequestDto requestDto) {
        return questionService.createQuestion(project_id, requestDto);
    }

    //문의글 수정 (작성자만 가능)
    @PatchMapping("/question/{question_id}")
    public ResponseEntity updateQuestion(@PathVariable Long question_id, @RequestBody QuestionRequestDto requestDto)
    {
        return questionService.updateQuestion(question_id, requestDto);
    }

    //문의글 삭제 (작성자만 가능)
    @DeleteMapping("/question/{question_id}")
    public ResponseEntity deleteQuestion(@PathVariable Long question_id)
    {
        return questionService.deleteQuestion(question_id);
    }


    // 문의 전체 조회
    @GetMapping("/{project_id}/question")
    public List<QuestionResponseDto> getQuestionList(@PathVariable Long project_id) {
        return questionService.getQuestionList(project_id);
    }

    // 문의 선택 조회 (비밀글일 때, 문의글 작성자 및 판매자만 글 조회 가능!) + 문의 답변도 함께 불러오기
    @GetMapping("/{project_id}/question/{question_id}")
    public QuestionSelectResponseDto getQuestion(@PathVariable Long project_id, @PathVariable Long question_id) {
        return questionService.getQuestion(project_id, question_id);
    }

    // 문의 답변 작성 (문의글 작성자 & 판매자만 작성 가능)
    @PostMapping("/{project_id}/question/{question_id}")
    public AnswerResponseDto createAnswer(@PathVariable Long project_id, @PathVariable Long question_id, @RequestBody AnswerRequestDto requestDto) {
        return questionService.createAnswer(project_id, question_id, requestDto);
    }

    // 문의 답변 수정 (판매자만 가능)
    @PatchMapping("/{project_id}/question/{question_id}/answer")
    public ResponseEntity updateAnswer(@PathVariable Long project_id, @PathVariable Long question_id, @RequestBody AnswerRequestDto requestDto)
    {
        return questionService.updateAnswer(project_id, question_id, requestDto);
    }

    //문의 답변 삭제 (판매자만 가능)
    @DeleteMapping("/{project_id}/question/{question_id}/answer")
    public ResponseEntity deleteAnswer(@PathVariable Long project_id, @PathVariable Long question_id)
    {
        return questionService.deleteAnswer(project_id, question_id);
    }

}