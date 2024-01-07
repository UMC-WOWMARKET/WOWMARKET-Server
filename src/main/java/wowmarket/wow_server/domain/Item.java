package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import wowmarket.wow_server.mypage.myproject.MySalesProject.dto.MySalesItemDto;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;
    private Long price;

    @ColumnDefault("0")
    private int goal;

    @ColumnDefault("0")
    private int limits; // 구매제한

    public void setProject(Project project){
        this.project = project;
    }

    public void modify(MySalesItemDto itemDto){
        this.name = itemDto.getItemName();
        this.price = itemDto.getPrice();
        this.goal = itemDto.getGoal();
        this.limits = itemDto.getLimits();
    }

}
