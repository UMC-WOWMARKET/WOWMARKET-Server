package wowmarket.wow_server.detail.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.detail.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.notice.dto.NoticeResponseDto;
import wowmarket.wow_server.detail.notice.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping("/api") //* path 추후에 다시 수정하기! *
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    // 공지 작성
    // * 판매자만 가능하게 권한 부여 필요 *
    @PostMapping("/notice")
    public NoticeResponseDto createNotice(@RequestBody NoticeRequestDto requestDto) {
        return noticeService.createNotice(requestDto);
    }

    // 공지 전체 조회
    @GetMapping("/notice")
    public List<NoticeResponseDto> getNoticeList() {
        return noticeService.getNoticeList();
    }

    // 공지 선택 조회
    @GetMapping("/notice/{id}")
    public NoticeResponseDto getNotice(@PathVariable Long id) {
        return noticeService.getNotice(id);
    }

}