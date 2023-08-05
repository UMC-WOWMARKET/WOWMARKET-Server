package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.DemandProject;

import java.util.List;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {
    List<DemandProject> findByNameContaining(String name);

    Page<DemandProject> findDemandProjectByUser_Id(Long seller_id, Pageable pageable);

}
