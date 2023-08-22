package com.test_task.skyeng_test_task.repositories;

import com.test_task.skyeng_test_task.entities.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT ev FROM Event ev " +
            "WHERE ev.postalItem.id=?1 " +
            "AND ev.type= com.test_task.skyeng_test_task.entities.entity_types.EventType.ARRIVED_AT_THE_POST_OFFICE " +
            "AND ev.activeStatus=true")
    Event findArrivaledActiveEvent(Long postalItemId);

    @Query("SELECT ev FROM Event ev " +
            "WHERE ev.postalItem.id=?1 " +
            "AND ev.type= com.test_task.skyeng_test_task.entities.entity_types.EventType.DELIVERED")
    Event findDeliveredEvent(Long postalItemId);
}
