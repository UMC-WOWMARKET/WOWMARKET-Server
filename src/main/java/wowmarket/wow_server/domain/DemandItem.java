package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private DemandProject demand_project;

    private String name;
    private Long price;
    private int goal;

    public void setDemandProject(DemandProject demandProject){
        this.demand_project=demandProject;
    }
}
