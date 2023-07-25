package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long buyerId, Pageable pageable);
}
