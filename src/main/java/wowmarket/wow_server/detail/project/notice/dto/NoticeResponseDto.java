package wowmarket.wow_server.detail.project.notice.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.Notice;
import wowmarket.wow_server.domain.User;

import java.time.LocalDateTime;

@Getter
public class NoticeResponseDto {
    private Long notice_id;        // 공지 게시글 구분을 위한 id 값
    private String title;       // 제목
    private String content;    // 작성 내용
    private LocalDateTime createdTime;        // 게시글 생성 날짜
    private LocalDateTime lastModifiedTime;;       // 게시글 수정 날짜

    public NoticeResponseDto(Notice notice) {
        this.notice_id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdTime = notice.getCreated_time();
        this.lastModifiedTime = notice.getLast_modified_time();
    }
}