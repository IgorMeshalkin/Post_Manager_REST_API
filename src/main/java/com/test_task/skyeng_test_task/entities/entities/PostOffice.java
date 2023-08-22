package com.test_task.skyeng_test_task.entities.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test_task.skyeng_test_task.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "post_offices")
@Data
public class PostOffice extends BaseEntity {
    @Column(name = "post_index")
    private Integer postIndex;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "postOffice", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Event> events;
}
