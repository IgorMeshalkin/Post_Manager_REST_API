package com.test.skyeng_test_task.repositories;

import com.test.skyeng_test_task.entities.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT ev FROM Event ev " +
            "WHERE ev.postalItem.id=?1 " +
            "AND ev.type= com.test.skyeng_test_task.entities.entity_types.EventType.ARRIVAL_TO_POST_OFFICE " +
            "AND ev.activeStatus=true")
    Event findArrivaledActiveEvent(Long postalItemId);

    @Query("SELECT ev FROM Event ev " +
            "WHERE ev.postalItem.id=?1 " +
            "AND ev.type= com.test.skyeng_test_task.entities.entity_types.EventType.DELIVERY_TO_RECIPIENT")
    Event findDeliveredEvent(Long postalItemId);
}
