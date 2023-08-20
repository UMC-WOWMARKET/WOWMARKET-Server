package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(nativeQuery = true, value = "select * from answer where question_id=?")
    Answer findByQuestionId(Long question_id);

    boolean existsByQuestionId(Long question_id);
}