package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DemandAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_question_id")
    private DemandQuestion demandQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_order_id")
    private DemandOrder demandOrder;

    private String answer;
}
