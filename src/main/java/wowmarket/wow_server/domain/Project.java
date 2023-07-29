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
    @Column(name = "projectId")
    private Long id;
    private String name;
    private String nickname;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;


    private LocalDate start_date;
    private LocalDate end_date;
    private int participant_number;
    private Boolean is_del;
    private Boolean is_end;

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


    public void setUser(User user){
        this.user = user;
    }
    public void setCategory(Category category){
        this.category = category;
    }

}

