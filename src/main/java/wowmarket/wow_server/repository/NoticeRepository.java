package wowmarket.wow_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wowmarket.wow_server.domain.Notice;
import wowmarket.wow_server.domain.Project;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long>  {
    @Query(nativeQuery = true, value = "select * from notice")
    List<Notice> findAllByOrderByCreated_timeDesc();

    @Query(nativeQuery = true, value = "select * from notice where project_id=?")
    List<Notice> findByProjectIdByOrderByCreated_timeDesc(Long project_id);

    @Query(nativeQuery = true, value = "select * from notice where notice_id=?")
    Optional<Notice> findByNoticeId(Long notice_id);
}