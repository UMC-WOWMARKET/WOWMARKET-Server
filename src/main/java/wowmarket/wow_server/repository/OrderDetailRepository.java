package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Order_detail;

public interface OrderDetailRepository extends JpaRepository<Order_detail, Long> {
}
