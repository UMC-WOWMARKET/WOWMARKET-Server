package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query(nativeQuery = true, value = "select * from order_detail where order_id = ?1")
    List<OrderDetail> findByOrders_Id(Long order_id);
}
