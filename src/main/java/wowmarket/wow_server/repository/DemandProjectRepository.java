package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandProject;

import java.util.List;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {
    List<DemandProject> findByNameContaining(String name);


}
