package com.test.skyeng_test_task.entities.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.skyeng_test_task.entities.BaseEntity;
import com.test.skyeng_test_task.entities.entity_types.EventType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
public class Event extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postal_item_id")
    @JsonIgnore
    private PostalItem postalItem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_office_id")
    @JsonManagedReference
    private PostOffice postOffice;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(name = "active_status")
    private Boolean activeStatus;

    /**
     * специальный конструктор для класса
     * {@link com.test.skyeng_test_task.services.EventService}
     **/
    public Event(PostOffice postOffice, PostalItem postalItem, EventType type) {
        this.postOffice = postOffice;
        this.postalItem = postalItem;
        this.type = type;
        if (type.equals(EventType.ARRIVED_AT_THE_POST_OFFICE)) {
            this.activeStatus = true;
        }
        super.created = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + super.getId() +
                ", type=" + type +
                ", activeStatus=" + activeStatus +
                "} ";
    }
}
