package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class DemandDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_item_id", referencedColumnName = "demand_item_id")
    private DemandItem demandItem;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "buyer_id", referencedColumnName = "user_id")
//    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_order_id", referencedColumnName = "demand_order_id")
    private DemandOrder demandOrder;

    @ColumnDefault("0")
    private int count;

    @Builder
    public DemandDetail(DemandOrder demandOrder, DemandItem demandItem, int count){
        this.demandOrder = demandOrder;
        this.demandItem = demandItem;
        this.count = count;
    }

}
