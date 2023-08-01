package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Orders;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findByUser_Id(Long buyerId, Pageable pageable);

    List<Orders> findByProject_Id(Long projectId);
}
