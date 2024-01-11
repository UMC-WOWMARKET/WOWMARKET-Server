package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandAnswer;

public interface DemandAnswerRepository extends JpaRepository<DemandAnswer, Long> {
}
