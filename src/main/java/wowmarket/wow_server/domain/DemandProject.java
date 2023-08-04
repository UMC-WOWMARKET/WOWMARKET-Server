package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
public class DemandProject extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_project_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private User user;
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    private String name;
    private String description;
    private LocalDate start_date;
    private LocalDate end_date;

    private String thumbnail;
    private String image1;
    private String image2;
    private String image3;

    @Setter
    private boolean is_end;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수

    public void increaseView(Long demand_project_id) {
        this.view += 1;
    }

    public void setUser(User user){ this.user = user;}
    public void setCategory(Category category){
        this.category = category;
    }

    public void setImage(List<String> uploaded){
        switch (uploaded.size()){
            case 4:
                image3 = uploaded.get(3);
            case 3:
                image2 = uploaded.get(2);
            case 2:
                image1 = uploaded.get(1);
            case 1:
                thumbnail = uploaded.get(0);
        }
    }
}
