package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.DemandOrder;

import java.util.Optional;

public interface DemandOrderRepository extends JpaRepository<DemandOrder, Long> {

    Page<DemandOrder> findByUser_Id(Long buyer_id, Pageable pageable);

    boolean existsByUser_Id(Long buyer_id);
}
