package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.Notice;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>  {
    @Query(nativeQuery = true, value = "select * from notice")
    List<Notice> findAllByOrderByCreated_timeDesc();
}