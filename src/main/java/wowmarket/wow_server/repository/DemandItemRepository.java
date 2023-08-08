package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wowmarket.wow_server.domain.DemandItem;
import wowmarket.wow_server.domain.DemandProject;

import java.util.List;

public interface DemandItemRepository extends JpaRepository<DemandItem, Long> {
    @Query("SELECT SUM(di.goal) FROM DemandItem di WHERE di.demandProject = :demand_project")
    int getTotalGoalByDemandProject(@Param("demand_project") DemandProject demandProject);

    @Query("SELECT COALESCE(SUM(dd.count), 0) FROM DemandDetail dd " +
            "WHERE dd.demandItem.demandProject = :demand_project")
    int getTotalOrderCountByDemandProject(@Param("demand_project") DemandProject demandProject);

    List<DemandItem> findDemandItemByDemandProject_Id(Long demand_project_id);
}
