package wowmarket.wow_server.detail.project.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    // 문의 전체 조회
    @GetMapping("/{project_id}/question")
    public List<QuestionResponseDto> getQuestionList(@PathVariable Long project_id) {
        return questionService.getQuestionList(project_id);
    }

    // 문의 선택 조회 (비밀글일 때, 문의글 작성자 및 판매자만 글 조회 가능!)
    @GetMapping("/{project_id}/question/{question_id}")
    public QuestionSelectResponseDto getQuestion(@PathVariable Long project_id, @PathVariable Long question_id) {
        return questionService.getQuestion(project_id, question_id);
    }

}