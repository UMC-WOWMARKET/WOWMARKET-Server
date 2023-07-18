package wowmarket.wow_server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Project {
    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String name;
    private String oneline;
}

