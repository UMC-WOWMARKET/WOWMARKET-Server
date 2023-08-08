package wowmarket.wow_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wowmarket.wow_server.domain.DemandProject;

import java.util.List;

public interface DemandProjectRepository extends JpaRepository<DemandProject, Long> {
    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.user.univ = :user_univ")
    List<DemandProject> findDemandProjectByUserUniv(@Param("user_univ") String user_univ);

    @Query("SELECT dp FROM DemandProject dp WHERE dp.isEnd = false")
    List<DemandProject> findAllDemandProjectNotEnd();

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.name like CONCAT('%', :search, '%') " +
            "AND dp.user.univ = :user_univ")
    List<DemandProject> findDemandProjectBySearchAndUserUniv(@Param("user_univ") String user_univ, @Param("search") String search);

    @Query("SELECT dp FROM DemandProject dp " +
            "WHERE dp.isEnd = false " +
            "AND dp.name like CONCAT('%', :search, '%')")
    List<DemandProject> findDemandProjectBySearch(@Param("search") String search);

    Page<DemandProject> findDemandProjectByUser_Id(Long seller_id, Pageable pageable);

}
