package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandItemDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class DemandItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_project_id", referencedColumnName = "demand_project_id")
    private DemandProject demandProject;

    private String name;
    private Long price;

    @ColumnDefault("0")
    private int goal;

    @ColumnDefault("0")
    private int limits;

    public void setDemandProject(DemandProject demandProject){
        this.demandProject=demandProject;
    }

    public void modify(MyDemandItemDto itemDto){
        this.name = itemDto.getName();
        this.price = itemDto.getPrice();
        this.goal = itemDto.getGoal();
        this.limits = itemDto.getLimits();
    }
}
