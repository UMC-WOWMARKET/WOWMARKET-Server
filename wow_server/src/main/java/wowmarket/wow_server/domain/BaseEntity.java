package wowmarket.wow_server.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    private LocalDateTime created_time;
    private LocalDateTime lastModified_time;
}
