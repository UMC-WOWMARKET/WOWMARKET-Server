package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wowmarket.wow_server.domain.DemandLikes;
import wowmarket.wow_server.domain.User;

import java.util.Optional;

@Repository
public interface DemandLikesRepository extends JpaRepository<DemandLikes, Long> {
    @Query("SELECT dl FROM DemandLikes dl " +
            "WHERE dl.user = :user AND dl.demandProject.id = :demandProjectId")
    Optional<DemandLikes> findByUserAndDemandProject(@Param("user") User user, @Param("demandProjectId") Long demandProjectId);

    @Modifying
    @Query("DELETE FROM DemandLikes dl WHERE dl.id = :demandLikesId")
    void deleteDemandLikes(@Param("demandLikesId") Long demandLikesId);
}
