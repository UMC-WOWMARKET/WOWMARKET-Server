package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.OrderAnswer;

public interface OrderAnswerRepository extends JpaRepository<OrderAnswer, Long> {
}
