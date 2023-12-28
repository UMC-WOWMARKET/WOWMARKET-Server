package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    /* 등록 */
    private String projectName; // 프로젝트 이름
    private String description; // 프로젝트 설명

    private String sellerName;  // 판매자명
    private String phoneNumber; // 전화번호
    private String email; // 이메일
    private String sellerEtc; // 기타 연락 수단

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private User user; // 판매자


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String thumbnail;
    private String image1;
    private String image2;
    private String image3;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private int participant_number;


    @Enumerated(EnumType.STRING)
    private ReceiveType receive_type; //delivery, pickup
    private String receive_address; //직접수령 시 픽업장소
    private String deliveryType; // 배송 시 배송 방법
    private Long delivery_fee; // 배송비


    private String bank; // 은행
    private String account; // 계좌
    private String account_holder_name; // 예금주

    @Column(columnDefinition = "tinyint(0) default 1")
    private boolean sellToAll; // 0-> 소속 대학 학생, 1-> 전체 학생

    @Column(columnDefinition="tinyint(0) default 0")
    @Setter
    private boolean isDel;

    @Column(columnDefinition="tinyint(0) default 0")
    @Setter
    private boolean isEnd;

    /* 조회 */
    private Long final_achievement_rate;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수


    public void setUser(User user){
        this.user = user;
    }
    public void setCategory(Category category){
        this.category = category;
    }

}


