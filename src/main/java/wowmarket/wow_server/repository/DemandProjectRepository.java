package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Project;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {
    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.user.univ = :user_univ")
    Page<DemandProject> findByUserUniv(@Param("user_univ") String user_univ, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp WHERE dp.isEnd = false")
    Page<DemandProject> findAllNotEnd(Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.name LIKE CONCAT('%', :search, '%') " +
            "AND dp.user.univ = :user_univ")
    Page<DemandProject> findBySearchUserUniv(@Param("search") String search, @Param("user_univ") String user_univ, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.name LIKE CONCAT('%', :search, '%')")
    Page<DemandProject> findBySearch(@Param("search") String search, Pageable pageable);

    Page<DemandProject> findDemandProjectByUser_Id(Long seller_id, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from demand_project where demand_project_id =?")
    DemandProject findByDemandProject_Id(Long demandProjectId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update demand_project p set p.view=p.view+1 where demand_project_id=?")
    int updateView(Long demandProjectId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update demand_project p set p.participant_number=p.participant_number+1 where demand_project_id=?")
    int updateParticipantNumber(Long demandProjectId);

}
