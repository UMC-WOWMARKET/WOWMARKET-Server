package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandQuestion;

import java.util.List;

public interface DemandQuestionRepository extends JpaRepository<DemandQuestion, Long> {
    List<DemandQuestion> findByDemandProject_Id(Long projectId);
}
