package wowmarket.wow_server.detail.project.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.detail.project.notice.dto.NoticeRequestDto;
import wowmarket.wow_server.detail.project.notice.dto.NoticeResponseDto;
import wowmarket.wow_server.detail.project.notice.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    // 주문폼: (판매자만) 공지 작성
    // 성공시: 200, 실패시: 400 반환
    @PostMapping("/{project_id}/notice")
    public ResponseEntity createNotice(@PathVariable Long project_id, @RequestBody NoticeRequestDto requestDto) {
        return noticeService.createNotice(project_id, requestDto);
    }


    // 주문폼: 공지 전체 조회
    @GetMapping("/{project_id}/notice")
    public List<NoticeResponseDto> getNoticeList(@PathVariable Long project_id) {
        return noticeService.getNoticeList(project_id);
    }

    // 주문폼: 공지 선택 조회
    @GetMapping("/{project_id}/notice/{notice_id}")
    public NoticeResponseDto getNotice(@PathVariable String project_id, @PathVariable Long notice_id) {
        return noticeService.getNotice(notice_id);
    }

}