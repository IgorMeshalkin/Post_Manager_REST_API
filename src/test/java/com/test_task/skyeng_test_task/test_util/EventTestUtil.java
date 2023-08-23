package com.test_task.skyeng_test_task.test_util;

import com.test_task.skyeng_test_task.entities.entities.Event;
import com.test_task.skyeng_test_task.entities.entities.PostOffice;
import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import com.test_task.skyeng_test_task.entities.entity_types.EventType;

public class EventTestUtil {

    public static Event getBeforeArrivedEvent(PostOffice postOffice, PostalItem postalItem) {
        return new Event(postOffice, postalItem, EventType.ARRIVED_AT_THE_POST_OFFICE);
    }

    public static Event getAfterArrivedEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setType(EventType.ARRIVED_AT_THE_POST_OFFICE);
        event.setActiveStatus(true);
        return event;
    }

    public static Event getAlreadyDeliveredEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setType(EventType.DELIVERED);
        return event;
    }
}
