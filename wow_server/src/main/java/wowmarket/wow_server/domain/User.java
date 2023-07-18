package wowmarket.wow_server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class User extends BaseEntity {
    @Id @GeneratedValue
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
