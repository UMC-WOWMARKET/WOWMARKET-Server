package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import wowmarket.wow_server.detail.project.question.dto.QuestionRequestDto;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor
public class Question extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "question_title", nullable=false)
    private String title;

    @Column(name = "question_content", nullable=false, length=500)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    //비밀글 여부
    @Column(name = "is_secret", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("false")
    private boolean secret;

    //문의글 작성
    public Question(Project project, User user, QuestionRequestDto requestDto){
        this.project = project;
        this.user = user;
        this.secret = requestDto.isSecret();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
