package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wowmarket.wow_server.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
