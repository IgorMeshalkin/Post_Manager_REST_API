package com.test.skyeng_test_task.entities.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.skyeng_test_task.entities.entity_types.PostalItemType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class Test {
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
    @JsonManagedReference
    @JsonIgnore
    private List<Event> events;
}
