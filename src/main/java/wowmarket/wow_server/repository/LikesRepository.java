package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wowmarket.wow_server.domain.Likes;
import wowmarket.wow_server.domain.User;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query("SELECT l FROM Likes l " +
            "WHERE l.user = :user AND l.project.id = :projectId")
    Optional<Likes> findByUserAndProject(@Param("user") User user, @Param("projectId") Long projectId);

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.id = :likesId")
    void deleteLikes(@Param("likesId") Long likesId);
}
