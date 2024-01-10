package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.OrderQuestion;

import java.util.List;

public interface OrderQuestionRepository extends JpaRepository<OrderQuestion, Long> {
    List<OrderQuestion> findByProject_Id(Long project_id);
}
