package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;
}
