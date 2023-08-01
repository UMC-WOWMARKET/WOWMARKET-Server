package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    private String name;
    private String description;
    private LocalDate start_date;
    private LocalDate end_date;
    //설명이미지필드
    //대표이미지필드
    //소개이미지1
    //소개이미지2
    //소개이미지3

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수

    public void setUser(User user){ this.user = user;}
    public void setCategory(Category category){
        this.category = category;
    }

}
