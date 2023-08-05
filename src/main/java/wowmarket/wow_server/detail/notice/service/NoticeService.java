package wowmarket.wow_server.detail.notice.service;

import org.springframework.stereotype.Service;
import wowmarket.wow_server.detail.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.notice.dto.NoticeResponseDto;
import wowmarket.wow_server.domain.Notice;
import wowmarket.wow_server.repository.NoticeRepository;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    //공지 작성
    public NoticeResponseDto createNotice(NoticeRequestDto requestDto) {
        Notice notice = new Notice(requestDto); // RequestDto -> Entity
        Notice saveNotice = noticeRepository.save(notice); // DB 저장
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(saveNotice); // Entity -> ResponseDto

        return noticeResponseDto;
    }

    // 공지 전체 조회
    public List<NoticeResponseDto> getNoticeList() {
        return noticeRepository.findAllByOrderByCreated_timeDesc().stream()  // DB 에서 조회한 List -> stream 으로 변환
                .map(NoticeResponseDto::new)  // stream 처리를 통해, Board 객체 -> BoardResponseDto 로 변환
                .toList(); // 다시 stream -> List 로 변환
    }

    // 공지 선택 조회
    public NoticeResponseDto getNotice(Long id) {
        // 해당 id 가 없을 경우
        Notice notice = noticeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("공지 아이디가 존재하지 않습니다.")
        );

        // 해당 id 가 있을 경우
        return new NoticeResponseDto(notice);
    }
}