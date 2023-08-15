package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.Notice;
import wowmarket.wow_server.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(nativeQuery = true, value = "select * from question")
    List<Question> findAllByOrderByCreated_timeDesc();

    @Query(nativeQuery = true, value = "select * from question where project_id=?")
    List<Question> findByProjectIdByOrderByCreated_timeDesc(Long project_id);

    @Query(nativeQuery = true, value = "select * from question where question_id=?")
    Optional<Question> findByQuestionId(Long question_id);
}
