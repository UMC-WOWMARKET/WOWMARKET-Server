package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
