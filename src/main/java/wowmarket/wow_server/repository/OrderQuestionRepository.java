package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.OrderQuestion;

public interface OrderQuestionRepository extends JpaRepository<OrderQuestion, Long> {
}
