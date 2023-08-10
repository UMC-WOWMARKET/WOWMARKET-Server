package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wowmarket.wow_server.domain.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p " +
            "WHERE p.isDel = false AND p.isEnd = false " +
            "AND  p.user.univ = :user_univ")
    Page<Project> findByUserUniv(@Param("user_univ") String user_univ, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.isDel = false AND p.isEnd = false")
    Page<Project> findAllNotDelNotEnd(Pageable pageable);

    @Query("SELECT p FROM Project p " +
            "WHERE p.isDel = false AND p.isEnd = false " +
            "AND p.name LIKE CONCAT('%', :search, '%') " +
            "AND p.user.univ = :user_univ")
    Page<Project> findBySearchUserUniv(@Param("search") String search, @Param("user_univ") String user_univ, Pageable pageable);

    @Query("SELECT p FROM Project p " +
            "WHERE p.isDel = false AND p.isEnd = false " +
            "AND p.name LIKE CONCAT('%', :search, '%')")
    Page<Project> findBySearch(@Param("search") String search, Pageable pageable);

    Page<Project> findByUser_Id(Long sellerId, Pageable pageable);

    List<Project> findByUser_Id(Long sellerId);
}