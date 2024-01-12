package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.Permission;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDateTime;
import java.util.List;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.startDate <= :currentDate AND dp.endDate >= :currentDate " +
            "AND dp.user.univ = :userUniv AND dp.permission = 'APPROVED'")
    Page<DemandProject> findByUserUniv(@Param("currentDate") LocalDateTime currentDate, @Param("userUniv") String userUniv, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp WHERE dp.isEnd = false " +
            "AND dp.startDate <= :currentDate AND dp.endDate >= :currentDate " +
            "AND dp.permission = 'APPROVED'")
    Page<DemandProject> findAllNotEnd(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.startDate <= :currentDate AND dp.endDate >= :currentDate " +
            "AND dp.projectName LIKE CONCAT('%', :search, '%') " +
            "AND dp.user.univ = :userUniv AND dp.permission = 'APPROVED'")
    Page<DemandProject> findBySearchUserUniv(@Param("currentDate") LocalDateTime currentDate, @Param("search") String search,
                                             @Param("userUniv") String userUniv, Pageable pageable);

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.startDate <= :currentDate AND dp.endDate >= :currentDate " +
            "AND dp.projectName LIKE CONCAT('%', :search, '%') AND dp.permission = 'APPROVED'")
    Page<DemandProject> findBySearch(@Param("currentDate") LocalDateTime currentDate, @Param("search") String search, Pageable pageable);

    Page<DemandProject> findDemandProjectByUser_Id(Long seller_id, Pageable pageable);

    List<DemandProject> findByUser_Id(Long sellerId);

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

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false AND dp.startDate <= :currentDate AND dp.endDate >= :currentDate")
    Page<DemandProject> findDemandProjectsPermission(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    @Modifying
    @Query("UPDATE DemandProject dp SET dp.permission = :permission WHERE dp.id = :demandProjectId")
    void updatePermission(@Param("permission") Permission permission, @Param("demandProjectId") Long demandProjectId);
}
