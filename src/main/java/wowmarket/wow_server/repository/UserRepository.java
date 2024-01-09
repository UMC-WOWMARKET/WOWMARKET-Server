package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wowmarket.wow_server.domain.User;

import java.util.List;
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

    @Modifying
    @Query("UPDATE User u SET u.demandLike = u.demandLike + 1 " +
            "WHERE u = :user")
    void updateDemandProjectLike(@Param("user") User user);

    @Modifying
    @Query("UPDATE User u SET u.demandLike = u.demandLike - 1 " +
            "WHERE u = :user")
    void updateDemandProjectUnLike(@Param("user") User user);


    @Query(nativeQuery = true, value = "SELECT * FROM user where role = 'ROLE_ADMIN'")
    List<User> findAdmin();

}
