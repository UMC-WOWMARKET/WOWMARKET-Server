package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Permission;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p " +
            "WHERE p.isDel = false AND p.isEnd = false " +
            "AND p.startDate <= :currentDate AND p.endDate >= :currentDate " +
            "AND p.user.univ = :userUniv")
    Page<Project> findByUserUniv(@Param("userUniv") String userUniv, @Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.isDel = false AND p.isEnd = false " +
            "AND p.startDate <= :currentDate AND p.endDate >= :currentDate")
    Page<Project> findAllNotDelNotEnd(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    @Query("SELECT p FROM Project p " +
            "WHERE p.isDel = false AND p.isEnd = false " +
            "AND p.startDate <= :currentDate AND p.endDate >= :currentDate " +
            "AND p.projectName LIKE CONCAT('%', :search, '%') " +
            "AND p.user.univ = :userUniv")
    Page<Project> findBySearchUserUniv(@Param("currentDate") LocalDateTime currentDate, @Param("search") String search,
                                       @Param("userUniv") String userUniv, Pageable pageable);

    @Query("SELECT p FROM Project p " +
            "WHERE p.isDel = false AND p.isEnd = false " +
            "AND p.startDate <= :currentDate AND p.endDate >= :currentDate " +
            "AND p.projectName LIKE CONCAT('%', :search, '%')")
    Page<Project> findBySearch(@Param("currentDate") LocalDateTime currentDate, @Param("search") String search, Pageable pageable);

    Page<Project> findByUser_Id(Long sellerId, Pageable pageable);

    List<Project> findByUser_Id(Long sellerId);

    @Query(nativeQuery = true, value = "select * from project where project_id =?")
    Project findByProject_Id(Long projectId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update project p set p.view=p.view+1 where project_id=?")
    int updateView(Long projectId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update project p set p.participant_number=p.participant_number+1 where project_id=?")
    int updateParticipantNumber(Long projectId);

    @Modifying
    @Query("UPDATE Project p SET p.likeCnt = p.likeCnt + 1 " +
            "WHERE p.id = :projectId")
    void updateProjectLike(@Param("projectId") Long projectId);

    @Modifying
    @Query("UPDATE Project p SET p.likeCnt = p.likeCnt - 1 " +
            "WHERE p.id = :projectId")
    void updateProjectUnLike(@Param("projectId") Long projectId);

    @Query("SELECT p FROM Project p " +
            "WHERE p.isDel = false AND p.isEnd = false AND p.endDate >= :currentDate")
    Page<Project> findProjectsAdmin(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    @Modifying
    @Query("UPDATE Project p SET p.permission = :permission WHERE p.id = :projectId")
    void updatePermission(@Param("permission") Permission permission, @Param("projectId") Long projectId);
}