package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDate;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {
    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.startDate <= :current_date AND dp.endDate >= :current_date " +
            "AND dp.user.univ = :user_univ")
    Page<DemandProject> findByUserUniv(@Param("current_date") LocalDate current_date, @Param("user_univ") String user_univ, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp WHERE dp.isEnd = false " +
            "AND dp.startDate <= :current_date AND dp.endDate >= :current_date")
    Page<DemandProject> findAllNotEnd(@Param("current_date") LocalDate current_date, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.startDate <= :current_date AND dp.endDate >= :current_date " +
            "AND dp.projectName LIKE CONCAT('%', :search, '%') " +
            "AND dp.user.univ = :user_univ")
    Page<DemandProject> findBySearchUserUniv(@Param("current_date") LocalDate current_date, @Param("search") String search,
                                             @Param("user_univ") String user_univ, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.startDate <= :current_date AND dp.endDate >= :current_date " +
            "AND dp.projectName LIKE CONCAT('%', :search, '%')")
    Page<DemandProject> findBySearch(@Param("current_date") LocalDate current_date, @Param("search") String search, Pageable pageable);

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

    @Modifying
    @Query("UPDATE DemandProject dp SET dp.likeCnt = dp.likeCnt + 1 " +
            "WHERE dp.id = :demandProjectId")
    void updateDemandProjectLike(@Param("demandProjectId") Long demandProjectId);

    @Modifying
    @Query("UPDATE DemandProject dp SET dp.likeCnt = dp.likeCnt - 1 " +
            "WHERE dp.id = :demandProjectId")
    void updateDemandProjectUnLike(@Param("demandProjectId") Long demandProjectId);

}
