package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandQuestion;

public interface DemandQuestionRepository extends JpaRepository<DemandQuestion, Long> {
}
