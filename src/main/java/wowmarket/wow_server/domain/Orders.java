package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@AttributeOverride(name = "created_time", column = @Column(name = "order_time"))
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private int total_price;
    private String receiver;

    private String delivery_msg;
    private String phone;

    private String bank;
    private String account;
    private String depositor;
    private LocalDateTime depositTime;

    private String address;
    private String address_detail;
    private String zipcode;

    @Setter
    private int order_status;

    @Column(columnDefinition="tinyint(0) default 0")
    @Setter
    private boolean isDel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Orders(Project project, User user, String receiver, String zipcode, String address, String address_detail, String phone, String depositor, LocalDateTime depositTime, String bank, String account){
        this.project = project;
        this.user = user;
        this.receiver = receiver;
        this.zipcode = zipcode;
        this.address = address;
        this.address_detail = address_detail;
        this.phone = phone;
        this.depositor = depositor;
        this.depositTime = depositTime;
        this.bank = bank;
        this.account = account;
    }

}