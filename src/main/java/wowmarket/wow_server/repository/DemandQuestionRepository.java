package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.DemandQuestion;
import java.util.List;

import java.util.List;

public interface DemandQuestionRepository extends JpaRepository<DemandQuestion, Long> {
    List<DemandQuestion> findByDemandProject_Id(Long demand_project_id);

    @Query(nativeQuery = true, value = "select * from demand_question where demand_question_id =?")
    DemandQuestion findByQuestion_Id(Long questionId);

}
