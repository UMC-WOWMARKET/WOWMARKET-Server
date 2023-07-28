package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
