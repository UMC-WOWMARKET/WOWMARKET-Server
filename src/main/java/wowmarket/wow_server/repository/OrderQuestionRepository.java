package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.OrderQuestion;
import wowmarket.wow_server.domain.Question;

import java.util.List;

public interface OrderQuestionRepository extends JpaRepository<OrderQuestion, Long> {
    List<OrderQuestion> findByProject_Id(Long project_id);

    @Query(nativeQuery = true, value = "select * from order_question where order_question_id =?")
    OrderQuestion findByQuestion_Id(Long questionId);
}
