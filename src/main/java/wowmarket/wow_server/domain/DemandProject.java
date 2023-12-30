package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    /* 등록 */
    private String sellerName;  // 판매자명
    private String phoneNumber; // 전화번호
    private String email; // 이메일
    private String sellerEtc; // 기타 연락 수단

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    private String thumbnail;
    private String image1;
    private String image2;
    private String image3;

    private String projectName;
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    private int participant_number;
    private Long final_achievement_rate;

    @Setter
    @Column(columnDefinition="tinyint(0) default 0")
    private boolean isEnd;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCnt;

    public void setUser(User user){ this.user = user;}
    public void setCategory(Category category){
        this.category = category;
    }

}
