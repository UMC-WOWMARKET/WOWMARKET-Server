package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;
    private Long price;
    private int goal;

}
