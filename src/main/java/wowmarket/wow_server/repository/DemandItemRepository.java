package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wowmarket.wow_server.domain.DemandItem;
import wowmarket.wow_server.domain.DemandProject;

import java.util.List;

public interface DemandItemRepository extends JpaRepository<DemandItem, Long> {
    @Query("SELECT SUM(di.goal) FROM DemandItem di WHERE di.demand_project = :demand_project")
    int getTotalGoalByDemandProject(@Param("demand_project") DemandProject demandProject);

    @Query("SELECT SUM(dd.count) FROM DemandDetail dd WHERE dd.demand_item.demand_project = :demand_project")
    int getTotalOrderCountByDemandProject(@Param("demand_project") DemandProject demandProject);
}
