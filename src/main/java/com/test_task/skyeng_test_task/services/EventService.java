package com.test_task.skyeng_test_task.services;

import com.test_task.skyeng_test_task.entities.entities.Event;
import com.test_task.skyeng_test_task.entities.entities.PostOffice;
import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import com.test_task.skyeng_test_task.entities.entity_types.EventType;
import com.test_task.skyeng_test_task.exceptions.IncorrectValuesException;
import com.test_task.skyeng_test_task.exceptions.PostItemAlreadyTakenException;
import com.test_task.skyeng_test_task.repositories.EventRepository;
import com.test_task.skyeng_test_task.repositories.PostOfficeRepository;
import com.test_task.skyeng_test_task.repositories.PostalItemRepository;
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
    public Event arrival(Long postOfficeId, Long postalItemId) throws PostItemAlreadyTakenException, IncorrectValuesException {
        Event lastActiveEvent = eventRepository.findArrivaledActiveEvent(postalItemId);
        Event deliveredEvent = eventRepository.findDeliveredEvent(postalItemId);
        if (lastActiveEvent == null && deliveredEvent == null) {
            PostalItem postalItem = postalItemRepository.findById(postalItemId).orElse(null);
            PostOffice postOffice = postOfficeRepository.findById(postOfficeId).orElse(null);
            if (postalItem != null && postOffice != null) {
                Event event = new Event(postOffice, postalItem, EventType.ARRIVED_AT_THE_POST_OFFICE);
                return eventRepository.save(event);
            } else {
                throw new IncorrectValuesException("PostOffice with id = " + postOfficeId  + " or PostalItem with id = " + postalItemId + " not found in database");
            }
        } else {
            throw new PostItemAlreadyTakenException("PostalItem with id = " + postalItemId + " is already delivered to the recipient or don't left from last PostOffice and can't be arrival to next PostOffice.");
        }
    }

    @Transactional
    public Event leaving(Long postOfficeId, Long postalItemId) throws IncorrectValuesException {
        Event lastActiveEvent = eventRepository.findArrivaledActiveEvent(postalItemId);
        if (lastActiveEvent != null && lastActiveEvent.getPostOffice().getId().equals(postOfficeId)) {
            lastActiveEvent.setActiveStatus(false);
            eventRepository.save(lastActiveEvent);

            PostalItem postalItem = postalItemRepository.findById(postalItemId).orElse(null);
            PostOffice postOffice = postOfficeRepository.findById(postOfficeId).orElse(null);
            Event newLeavingEvent = new Event(postOffice, postalItem, EventType.LEFT_POST_OFFICE);
            return eventRepository.save(newLeavingEvent);
        } else {
            throw new IncorrectValuesException("PostalItem with id = " + postalItemId + " is not in PostOffice with id = " + postOfficeId);
        }
    }

    @Transactional
    public Event delivery(Long postalItemId) throws PostItemAlreadyTakenException {
        Event lastActiveEvent = eventRepository.findArrivaledActiveEvent(postalItemId);
        Event deliveredEvent = eventRepository.findDeliveredEvent(postalItemId);
        if (lastActiveEvent == null && deliveredEvent == null) {
            PostalItem postalItem = postalItemRepository.findById(postalItemId).orElse(null);
            Event newDeliveryEvent = new Event(null, postalItem, EventType.DELIVERED);
            return eventRepository.save(newDeliveryEvent);
        } else {
            throw new PostItemAlreadyTakenException("PostalItem with id = " + postalItemId + " is already delivered to the recipient or don't left from last PostOffice and can't be delivery.");
        }
    }
}
