package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wowmarket.wow_server.domain.DemandItem;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.Project;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByProject(Project project);

    @Query("SELECT SUM(i.goal) FROM Item i WHERE i.project = :project ")
    int getTotalGoalByProject(@Param("project") Project project);

    @Query("SELECT COALESCE(SUM(od.count), 0) FROM OrderDetail od " +
            "WHERE od.item.project = :project " +
            "AND od.orders.isDel = false")
    int getTotalOrderCountByProject(@Param("project") Project project);
    //OrderDetail이 아닌 Item에서 주문 개수를 고려하여 프로젝트 별 주문 개수의 합을 구하는 쿼리

    List<Item> findByProject_Id(Long project_id);

    Item findItemById(Long item_id); //item_id로 찾기

}