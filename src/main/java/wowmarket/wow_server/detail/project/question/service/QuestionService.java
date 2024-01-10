package wowmarket.wow_server.detail.project.question.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.project.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.project.question.dto.AnswerRequestDto;
import wowmarket.wow_server.detail.project.question.dto.AnswerResponseDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionRequestDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionResponseDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionSelectResponseDto;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.QuestionRepository;
import wowmarket.wow_server.repository.UserRepository;
import wowmarket.wow_server.repository.AnswerRepository;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;


    public QuestionService(ProjectRepository projectRepository, UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    //문의 작성: 누구나 가능!
    public QuestionResponseDto createQuestion(Long project_id, QuestionRequestDto requestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Project project = projectRepository.findByProject_Id(project_id);
        Question question = new Question(project, user, requestDto); // RequestDto -> Entity
        Question saveQuestion = questionRepository.save(question); // DB 저장
        QuestionResponseDto questionResponseDto = new QuestionResponseDto(saveQuestion); // Entity -> ResponseDto

        return questionResponseDto;
    }

    //문의 수정 (글 작성자만 가능)
    public ResponseEntity updateQuestion(Long question_id, QuestionRequestDto requestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Question question = questionRepository.findByQuestion_Id(question_id);
        //문의글 작성자만 글 수정 가능
        if (user.getEmail() == question.getUser().getEmail()){
            question.update(requestDto.getTitle(), requestDto.getContent(), requestDto.isSecret());
            questionRepository.save(question);
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST); //문의글 작성자가 아닐 경우
    }

    //문의 삭제 (작성자만 가능)
    public ResponseEntity deleteQuestion(Long question_id) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Question question = questionRepository.findByQuestion_Id(question_id);
        //판매자만 문의 삭제 가능
        if (user.getEmail() == question.getUser().getEmail()){
            //문의 삭제시 문의 답변글도 함께 삭제
            if (answerRepository.existsByQuestionId(question_id)) //문의 답변이 존재하는 경우
            {
                Answer answer = answerRepository.findByQuestionId(question_id);
                answerRepository.deleteById(answer.getId());
            }
            questionRepository.deleteById(question_id);
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST); //판매자 아닐 경우
    }


    // 문의 전체 조회 (비밀글 여부 Response로 출력 ok)
    public List<QuestionResponseDto> getQuestionList(Long project_id) {
        return questionRepository.findByProjectIdByOrderByCreated_timeDesc(project_id).stream()  // DB 에서 조회한 List -> stream 으로 변환
                .map(QuestionResponseDto::new)  // stream 처리를 통해, Question 객체 -> QuestionResponseDto 로 변환
                .toList(); // 다시 stream -> List 로 변환
    }

    // 문의 선택 조회 (문의 + 문의 답변 표시)
    public QuestionSelectResponseDto getQuestion(Long project_id, Long question_id) {
        Project project = projectRepository.findByProject_Id(project_id);
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElse(null);
        // 해당 id 가 없을 경우 (예외처리)
        Question question = questionRepository.findByProjectIdAndQuestionId(project_id, question_id).orElseThrow(
                () -> new IllegalArgumentException("공지 아이디가 존재하지 않습니다.")
        );

        Answer answer = answerRepository.findByQuestionId(question_id);

        //비밀글인 경우: 작성자 & 판매자만 조회 가능!
        if(question.isSecret()==true)
        {
            if(user!=null && (question.getUser() == user || project.getUser() == user)) //user가 null이 아니면서, 작성자or판매자
            {
                if (answerRepository.existsByQuestionId(question_id)) //문의 답변이 존재하는 경우
                {
                    AnswerResponseDto answerResponseDto = new AnswerResponseDto(answer);
                    return new QuestionSelectResponseDto(question, answerResponseDto, user.getId(), project.getUser().getId());
                }
                //문의 답변이 존재하지 않으면, 문의만 보여주기
                return new QuestionSelectResponseDto(question, user.getId(), project.getUser().getId());
            }
            else//user가 null이거나 사용자가 작성자or판매자가 아니라면
            {
                return null;
            }
        }


        //비밀글이 아닌 경우: 모두 문의 조회 가능!
        //이때, 사용자가 null인지 아닌지에 따라 매개변수 값 달라짐!
        if (user == null) {
            if (answerRepository.existsByQuestionId(question_id)) //문의 답변이 존재하는 경우
            {
                AnswerResponseDto answerResponseDto = new AnswerResponseDto(answer);
                return new QuestionSelectResponseDto(question, answerResponseDto);
            }

            //문의 답변이 존재하지 않으면, 문의만 보여주기
            return new QuestionSelectResponseDto(question);
        }

        else  //사용자가 null이 아닌 경우
        {
        if (answerRepository.existsByQuestionId(question_id)) //문의 답변이 존재하는 경우
        {
            AnswerResponseDto answerResponseDto = new AnswerResponseDto(answer);
            return new QuestionSelectResponseDto(question, answerResponseDto, user.getId(), project.getUser().getId());
        }

        //문의 답변이 존재하지 않으면, 문의만 보여주기
        return new QuestionSelectResponseDto(question, user.getId(), project.getUser().getId());
        }
    }


    // 문의 답변 작성 (판매자만 작성 가능)
    public AnswerResponseDto createAnswer(Long project_id, Long question_id, AnswerRequestDto requestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Project project = projectRepository.findByProject_Id(project_id);
        Question question = questionRepository.findByProjectIdAndQuestionId(project_id, question_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));

        //판매자만 답변 작성 가능
        if(project.getUser() == user)
        {
            Answer answer = new Answer(project, question, user, requestDto); // RequestDto -> Entity
            Answer saveAnswer = answerRepository.save(answer); // DB 저장
            AnswerResponseDto answerResponseDto = new AnswerResponseDto(saveAnswer); // Entity -> ResponseDto

            return answerResponseDto;
        }
        return null;
    }

    //문의 답변 수정 (판매자만 가능)
    public ResponseEntity updateAnswer(Long project_id, Long question_id, AnswerRequestDto requestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Project project = projectRepository.findByProject_Id(project_id);
        //판매자만 문의 답변 수정 가능
        if (user.getEmail() == project.getUser().getEmail()){
            Answer answer = answerRepository.findByQuestionId(question_id);
            answer.update(requestDto.getContent());
            answerRepository.save(answer);
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST); //판매자 아닐 경우
    }

    //문의 답변 삭제 (판매자만 가능)
    public ResponseEntity deleteAnswer(Long project_id, Long question_id) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Project project = projectRepository.findByProject_Id(project_id);
        //판매자만 문의 답변 삭제 가능
        if (user.getEmail() == project.getUser().getEmail()){
            Answer answer = answerRepository.findByQuestionId(question_id);
            answerRepository.deleteById(answer.getId());
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST); //판매자 아닐 경우
    }
}