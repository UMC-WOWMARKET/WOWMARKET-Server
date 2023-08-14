package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.detail.project.notice.dto.NoticeRequestDto;


@Entity
@Getter
@Table(name = "notice")
@NoArgsConstructor
public class Notice extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "notice_title", nullable=false)
    private String title;

    @Column(name = "notice_content", nullable=false, length=500)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    public void setUser(User user){
        this.user = user;
    }

    // 게시글 작성
    public Notice(Project project, User user, NoticeRequestDto requestDto) {
        this.project = project;
        this.user = user;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
