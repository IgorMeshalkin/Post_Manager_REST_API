package com.test_task.skyeng_test_task.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreatedDate
    @Column(name = "created")
    protected LocalDateTime created;

    @LastModifiedDate
    @Column(name = "last_updated")
    protected LocalDateTime lastUpdated;

}