package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
