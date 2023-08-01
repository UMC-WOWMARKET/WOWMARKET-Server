package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private String nickname;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    private LocalDate start_date;
    private LocalDate end_date;
    private int participant_number;

    @Column(columnDefinition="tinyint(0) default 0")
    @Setter
    private boolean is_del;

    @Column(columnDefinition="tinyint(0) default 0")
    @Setter
    private boolean is_end;

//    private Blob thumbnail;
//    private Blob image1;
//    private Blob image2;
//    private Blob image3;

    private String receive_type;
    private String bank;
    private String account;
    private String inquired_phone;
    private String inquired_email;

    private int re_progress;
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

