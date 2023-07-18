package wowmarket.wow_server;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
public class Project {
    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    private String name;
    private String oneline;
    private LocalDateTime startDate;
}
