package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandOrder;

public interface DemandOrderRepository extends JpaRepository<DemandOrder, Long> {

    Page<DemandOrder> findByUser_Id(Long buyer_id, Pageable pageable);
}
