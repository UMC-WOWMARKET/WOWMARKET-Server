package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContaining(String name);

    Page<Project> findByUser_Id(Long sellerId, Pageable pageable);

    List<Project> findByUser_Id(Long sellerId);
}