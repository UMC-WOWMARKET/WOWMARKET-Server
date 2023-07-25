package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String name;
    private int age;
    private Boolean Gender;
    private String address;
    private String bank;
    private String account;
    private String phone;
    private String email;
    private String univ;
    private LocalDateTime univ_auth;
    private Boolean univ_check;
    private Boolean is_seller;
    private Boolean marketing_agree;

}
