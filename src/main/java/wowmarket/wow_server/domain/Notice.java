package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.detail.notice.dto.NoticeRequestDto;

@Entity
@Getter
@Table(name = "notice")
@NoArgsConstructor
public class Notice extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    //User 테이블 관련 로직
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "notice_title", nullable=false)
    private String title;

    @Column(name = "notice_content", nullable=false, length=500)
    private String content;

    // 게시글 작성
    public Notice(NoticeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

//    // 게시글 수정
//    public void update(NoticeRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.content = requestDto.getContent();
//    }
}
