package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.detail.project.question.dto.AnswerRequestDto;

@Entity
@Getter
@Table(name = "answer")
@NoArgsConstructor
public class Answer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "answer_title", nullable=false)
    private String title;

    @Column(name = "answer_content", nullable=false, length=500)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

//    //비밀글 여부
//    @Column(name = "is_secret", nullable = false, columnDefinition = "TINYINT(1)")
//    @ColumnDefault("false")
//    private boolean secret;

//    @OneToOne(fetch=FetchType.LAZY)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    //답변 작성
    public Answer(Project project, User user, AnswerRequestDto requestDto){
        this.project = project;
        this.user = user;
//        this.secret = requestDto.isSecret();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
