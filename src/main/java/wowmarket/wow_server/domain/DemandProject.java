package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandProjectModifyRequestDto;

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

    @Enumerated(EnumType.STRING)
    @ColumnDefault("\"AWAIT\"")
    private Permission permission;

    @Setter
    @Column(columnDefinition="tinyint(0) default 0")
    private boolean isEnd;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCnt;

    @Column(columnDefinition = "tinyint(0) default 1")
    private boolean sellToAll; // 0-> 소속 대학 학생, 1-> 전체 학생

    public void setUser(User user){ this.user = user;}
    public void setCategory(Category category){
        this.category = category;
    }

    public void modify(MyDemandProjectModifyRequestDto requestDto, Category category){
        this.projectName = requestDto.getProjectName();
        this.description = requestDto.getDescription();
        this.sellerName = requestDto.getSellerName();
        this.phoneNumber = requestDto.getSellerPhoneNumber();
        this.email = requestDto.getSellerEmail();
        this.sellerEtc = requestDto.getSellerEtc();
        this.thumbnail = requestDto.getThumbnail();
        this.image1 = requestDto.getImage1();
        this.image2 = requestDto.getImage2();
        this.image3 = requestDto.getImage3();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.category = category;
    }
}
