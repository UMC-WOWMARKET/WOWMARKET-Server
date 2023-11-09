package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private User user;
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String thumbnail;
    private String image1;
    private String image2;
    private String image3;

    private LocalDate startDate;
    private LocalDate endDate;
    private int participant_number;

    @Column(columnDefinition="tinyint(0) default 0")
    @Setter
    private boolean isDel;

    @Column(columnDefinition="tinyint(0) default 0")
    @Setter
    private boolean isEnd;


    private String receive_type; //delivery, pickup
    private String receive_address; //직접수령 시 픽업장소
    private Long delivery_fee; //배송비
    private String address_detail;
    private String zipcode;

    private String bank;
    private String account;
    private String account_holder_name;


    private Long final_achievement_rate;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수

    @Column(columnDefinition = "tinyint(0) default 1")
    private boolean sellToAll; // 0-> 소속 대학 학생, 1-> 전체 학생

    public void setUser(User user){
        this.user = user;
    }
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


