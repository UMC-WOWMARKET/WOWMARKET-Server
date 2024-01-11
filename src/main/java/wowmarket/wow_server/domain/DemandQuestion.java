package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DemandQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_project_id")
    @Setter
    private DemandProject demandProject;

    @Column(nullable = false)
    private String question;

    @Column(columnDefinition="tinyint(0) default 0")
    private boolean essential;
}
