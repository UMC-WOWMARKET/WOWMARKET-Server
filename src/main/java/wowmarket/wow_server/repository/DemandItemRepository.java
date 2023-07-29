package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandItem;

public interface DemandItemRepository extends JpaRepository<DemandItem, Long> {
}
