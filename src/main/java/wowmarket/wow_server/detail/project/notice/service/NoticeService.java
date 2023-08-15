package wowmarket.wow_server.detail.project.notice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.project.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.project.notice.dto.NoticeResponseDto;
import wowmarket.wow_server.domain.Notice;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.NoticeRepository;
import wowmarket.wow_server.repository.ProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.List;

@Service
public class NoticeService {
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final ProjectRepository projectRepository;

    public NoticeService(UserRepository userRepository, ProjectRepository projectRepository, NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    //공지 작성
    public ResponseEntity createNotice(Long project_id, NoticeRequestDto requestDto) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Project project = projectRepository.findByProject_Id(project_id);
        //판매자만 공지 작성 가능
        if (user.getEmail() == project.getUser().getEmail()){
            Notice notice = new Notice(project, user, requestDto); // RequestDto -> Entity
            noticeRepository.save(notice); // DB 저장
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // 공지 전체 조회
    public List<NoticeResponseDto> getNoticeList(Long project_id) {
        return noticeRepository.findByProjectIdByOrderByCreated_timeDesc(project_id).stream()  // DB 에서 조회한 List -> stream 으로 변환
                .map(NoticeResponseDto::new)  // stream 처리를 통해, Board 객체 -> BoardResponseDto 로 변환
                .toList(); // 다시 stream -> List 로 변환
    }

    // 공지 선택 조회
    public NoticeResponseDto getNotice(Long notice_id) {
        Notice notice = noticeRepository.findByNoticeId(notice_id).orElseThrow(
                () -> new IllegalArgumentException("공지 아이디가 존재하지 않습니다.")
        );
        // 해당 id 가 있을 경우
        return new NoticeResponseDto(notice);
    }
}