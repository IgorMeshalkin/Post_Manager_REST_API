package com.test.skyeng_test_task.services;

import com.test.skyeng_test_task.entities.entities.Event;
import com.test.skyeng_test_task.entities.entities.PostOffice;
import com.test.skyeng_test_task.entities.entities.PostalItem;
import com.test.skyeng_test_task.entities.entity_types.EventType;
import com.test.skyeng_test_task.repositories.EventRepository;
import com.test.skyeng_test_task.repositories.PostOfficeRepository;
import com.test.skyeng_test_task.repositories.PostalItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final PostOfficeRepository postOfficeRepository;
    private final PostalItemRepository postalItemRepository;

    public EventService(EventRepository eventRepository, PostOfficeRepository postOfficeRepository, PostalItemRepository postalItemRepository) {
        this.eventRepository = eventRepository;
        this.postOfficeRepository = postOfficeRepository;
        this.postalItemRepository = postalItemRepository;
    }

    @Transactional
    public Event arrival(Long postOfficeId, Long postalItemId) {
        Event lastActiveEvent = eventRepository.findArrivaledActiveEvent(postalItemId);
        Event deliveredEvent = eventRepository.findDeliveredEvent(postalItemId);
        if (lastActiveEvent == null && deliveredEvent == null) {
            PostalItem postalItem = postalItemRepository.findById(postalItemId).orElse(null);
            PostOffice postOffice = postOfficeRepository.findById(postOfficeId).orElse(null);
            if (postalItem != null && postOffice != null) {
                Event event = new Event(postOffice, postalItem, EventType.ARRIVAL_TO_POST_OFFICE);
                return eventRepository.save(event);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public Event leaving(Long postOfficeId, Long postalItemId) {
        Event lastActiveEvent = eventRepository.findArrivaledActiveEvent(postalItemId);
        if (lastActiveEvent != null && lastActiveEvent.getPostOffice().getId().equals(postOfficeId)) {
            lastActiveEvent.setActiveStatus(false);
            eventRepository.save(lastActiveEvent);

            PostalItem postalItem = postalItemRepository.findById(postalItemId).orElse(null);
            PostOffice postOffice = postOfficeRepository.findById(postOfficeId).orElse(null);
            Event newLeavingEvent = new Event(postOffice, postalItem, EventType.LEAVING_FROM_POST_OFFICE);
            return eventRepository.save(newLeavingEvent);
        } else {
            return null;
        }
    }

    @Transactional
    public Event delivery(Long postalItemId) {
        Event lastActiveEvent = eventRepository.findArrivaledActiveEvent(postalItemId);
        Event deliveredEvent = eventRepository.findDeliveredEvent(postalItemId);
        if (lastActiveEvent == null && deliveredEvent == null) {
            PostalItem postalItem = postalItemRepository.findById(postalItemId).orElse(null);
            Event newDeliveryEvent = new Event(null, postalItem, EventType.DELIVERY_TO_RECIPIENT);
            return eventRepository.save(newDeliveryEvent);
        } else {
            return null;
        }
    }
}
