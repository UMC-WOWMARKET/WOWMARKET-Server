package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String name;
    private String address;
    private String address_detail;
    private String bank;
    private String account;
    private String phone;
    @Column(unique = true, nullable = false)
    private String email;
    private String univ;
    private String univ_certified_date;
    private boolean univ_check = false;
    private boolean marketing_agree = false;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String refreshToken;
    private boolean temporary_password = false;

    @Enumerated(EnumType.STRING)
    private Login_Method login_method;

    public void updateUniv(String univ, String univ_certified_date, boolean univ_check) {
        this.univ = univ;
        this.univ_certified_date = univ_certified_date;
        this.univ_check = univ_check;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    // 비밀번호 변경 시 임시비밀번호인지도 확인
    public void updatePassword(String password, Boolean temp){ this.password = password; this.temporary_password = temp; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(role.name()));
        return auth;
    }


    /**
     * UserDetails
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


/////***
//    //== 회원탈퇴 -> 작성한 게시물, 댓글 모두 삭제 ==//
//    @OneToMany(mappedBy = "writer", cascade = ALL, orphanRemoval = true)
//    private List<Notice> noticeList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "writer", cascade = ALL, orphanRemoval = true)
//    private List<Question> questionList = new ArrayList<>();
//


}
