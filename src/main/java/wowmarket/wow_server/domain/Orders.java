package wowmarket.wow_server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@AttributeOverride(name = "created_time", column = @Column(name = "order_time"))
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;

    private int total_price;
    private String receiver;
    private String address;
    private String delivery_msg;
    private String phone;
    private String bank;
    private String account;
    private String depositor;
    //    private LocalDateTime deposit_time;
    private int order_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;

//    아직 주문상세 안 만들어서 주석처리
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_detail_id")
//    private Order_detail order_detail;
}