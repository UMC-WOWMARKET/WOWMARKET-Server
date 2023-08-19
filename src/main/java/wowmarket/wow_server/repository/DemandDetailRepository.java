package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.DemandDetail;

public interface DemandDetailRepository extends JpaRepository<DemandDetail, Long> {
    boolean existsByUser_Id(Long buyer_id);
}
