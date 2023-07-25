package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class DemandDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demandDetailId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandItemId", referencedColumnName = "demandItemId")
    private DemandItem demand_item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId", referencedColumnName = "userId")
    private User user;
    private Integer count;

}
