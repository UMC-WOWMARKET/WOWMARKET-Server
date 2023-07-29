package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandProject;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {
}
