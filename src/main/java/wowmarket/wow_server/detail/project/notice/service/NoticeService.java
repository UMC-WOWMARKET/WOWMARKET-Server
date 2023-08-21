package wowmarket.wow_server.detail.project.notice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.project.notice.dto.NoticePageResponseDto;
import wowmarket.wow_server.detail.project.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.project.notice.dto.NoticeResponseDto;
import wowmarket.wow_server.detail.project.notice.dto.NoticeSelectResponseDto;
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

    // 공지 목록 조회
    public NoticePageResponseDto getNoticeList(Long project_id) {
        //현재 로그인된 사용자 조회
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElse(null); //로그인된 사용자 없을시, user를 null로 설정
        //프로젝트 조회
        Project project = projectRepository.findByProject_Id(project_id);
        List<NoticeResponseDto> noticeList = noticeRepository.findByProjectIdByOrderByCreated_timeDesc(project_id).stream()  // DB 에서 조회한 List -> stream 으로 변환
                .map(NoticeResponseDto::new)  // stream 처리를 통해, Notice 객체 -> NoticeResponseDto 로 변환
                .toList(); // 다시 stream -> List 로 변환
        NoticePageResponseDto responseDto;//공지 목록, 현재 로그인된 사용자 user_id, 프로젝트 seller_id
        if (user == null)
        {
            responseDto = new NoticePageResponseDto(noticeList);
        }
        else{
            responseDto = new NoticePageResponseDto(noticeList, user.getId(), project.getUser().getId());
        }
        return responseDto;
    }

    // 공지 선택 조회
    public NoticeSelectResponseDto getNotice(Long notice_id) {
        Notice notice = noticeRepository.findByNoticeId(notice_id).orElseThrow(
                () -> new IllegalArgumentException("공지 아이디가 존재하지 않습니다.")
        );
        // 해당 id 가 있을 경우
        return new NoticeSelectResponseDto(notice);
    }
}