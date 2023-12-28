package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wowmarket.wow_server.domain.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.projectLike = u.projectLike + 1 " +
            "WHERE u = :user")
    void updateProjectLike(@Param("user") User user);

    @Modifying
    @Query("UPDATE User u SET u.projectLike = u.projectLike - 1 " +
            "WHERE u = :user")
    void updateProjectUnLike(@Param("user") User user);
}
