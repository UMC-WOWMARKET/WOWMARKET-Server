package wowmarket.wow_server.detail.project.question.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.project.question.dto.AnswerRequestDto;
import wowmarket.wow_server.detail.project.question.dto.AnswerResponseDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionRequestDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionResponseDto;
import wowmarket.wow_server.detail.project.question.dto.QuestionSelectResponseDto;
import wowmarket.wow_server.domain.Answer;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.Question;
import wowmarket.wow_server.domain.User;
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
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        // 해당 id 가 없을 경우 (예외처리)
        Question question = questionRepository.findById(question_id).orElseThrow(
                () -> new IllegalArgumentException("공지 아이디가 존재하지 않습니다.")
        );

        List<AnswerResponseDto> answer = answerRepository.findByQuestionId(question_id).stream()
                .map(AnswerResponseDto::new)
                .toList();

        //비밀글인 경우: 작성자 & 판매자만 조회 가능!
        if(question.isSecret()==true)
        {
            if(question.getUser() == user || project.getUser() == user)
            {
                return new QuestionSelectResponseDto(question, answer);
            }
            else
            {
                return null;
            }
        }
        //비밀글이 아닌 경우: 모두 문의 조회 가능!
        return new QuestionSelectResponseDto(question, answer);
    }


    // 문의 답변 작성 (문의글 작성자 & 판매자만 작성 가능)
    public AnswerResponseDto createAnswer(Long project_id, Long question_id, AnswerRequestDto requestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Project project = projectRepository.findByProject_Id(project_id);
        Question question = questionRepository.findByQuestionId(question_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));

        //문의글 작성자 & 판매자만 답변 작성 가능
        if(question.getUser() == user || project.getUser() == user)
        {
            Answer answer = new Answer(project, user, requestDto); // RequestDto -> Entity
            Answer saveAnswer = answerRepository.save(answer); // DB 저장
            AnswerResponseDto answerResponseDto = new AnswerResponseDto(saveAnswer); // Entity -> ResponseDto

            return answerResponseDto;
        }

        return null;
    }
}