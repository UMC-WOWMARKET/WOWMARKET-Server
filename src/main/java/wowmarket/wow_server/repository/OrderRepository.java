package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
