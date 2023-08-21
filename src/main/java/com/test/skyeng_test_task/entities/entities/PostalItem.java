package com.test.skyeng_test_task.entities.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.skyeng_test_task.entities.BaseEntity;
import com.test.skyeng_test_task.entities.entity_types.PostalItemType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "postal_items")
@Data
public class PostalItem extends BaseEntity {
    @Column(name = "track_number")
    private String trackNumber;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PostalItemType type;

    @Column(name = "recipient_index")
    private Integer recipientIndex;

    @Column(name = "recipient_address")
    private String recipientAddress;

    @Column(name = "recipient_name")
    private String recipientName;

    @OneToMany(mappedBy = "postalItem", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Event> events;
}
