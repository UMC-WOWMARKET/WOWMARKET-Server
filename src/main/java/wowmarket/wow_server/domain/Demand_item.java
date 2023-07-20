package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Demand_item extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_project_id", referencedColumnName = "demand_project_id")
    private Demand_project demand_project;

//    @ManyToOne(fetch = FetchType.LAZY) 이렇게 하면 user 테이블과 직접 연관관계 만들어지는거 아닌가?
//    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
//    private User user;

//    @ManyToOne(fetch = FetchType.LAZY) 이렇게 해야하는건가?
//    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
//    private Demand_project demand_project_seller_id;


    private String name;
    private Long price;
    private Long goal;
}
