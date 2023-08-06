package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wowmarket.wow_server.domain.DemandProject;

import java.util.List;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {
    List<DemandProject> findByNameContaining(String name);

    @Query("SELECT dp FROM DemandProject dp WHERE dp.user.univ = :user_univ")
    List<DemandProject> findDemandProjectByUserUniv(@Param("user_univ") String user_univ);

    Page<DemandProject> findDemandProjectByUser_Id(Long seller_id, Pageable pageable);

}
