package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import wowmarket.wow_server.converter.ReceiveTypeConverter;
import wowmarket.wow_server.mypage.myproject.MySalesProject.dto.MySalesProjectModifyRequestDto;

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
    @Convert(converter = ReceiveTypeConverter.class)
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

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCnt;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("\"AWAIT\"")
    private Permission permission;

    public void setUser(User user){
        this.user = user;
    }
    public void setCategory(Category category){
        this.category = category;
    }

    public void modify(MySalesProjectModifyRequestDto requestDto, Category category){
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
        this.receive_type = ReceiveType.ofReceiveType(requestDto.getReceiveType());
        this.receive_address = requestDto.getReceiveAddress();
        this.delivery_fee = requestDto.getDeliveryFee();
        this.bank = requestDto.getSellerBank();
        this.account = requestDto.getSellerAccount();
        this.account_holder_name = requestDto.getSellerAccountName();
        this.category = category;
        //this.sellToAll = sellToAll;
    }

}


