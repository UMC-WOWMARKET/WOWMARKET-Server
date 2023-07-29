package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
