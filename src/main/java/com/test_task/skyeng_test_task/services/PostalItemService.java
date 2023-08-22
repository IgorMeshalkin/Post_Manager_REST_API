package com.test_task.skyeng_test_task.services;

import com.test_task.skyeng_test_task.entities.entities.Event;
import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import com.test_task.skyeng_test_task.entities.entity_types.EventType;
import com.test_task.skyeng_test_task.repositories.EventRepository;
import com.test_task.skyeng_test_task.repositories.PostalItemRepository;
import com.test_task.skyeng_test_task.utils.PostalItemUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostalItemService {
    private final PostalItemRepository postalItemsRepository;
    private final EventRepository eventRepository;

    public PostalItemService(PostalItemRepository postalItemsRepository, EventRepository eventRepository) {
        this.postalItemsRepository = postalItemsRepository;
        this.eventRepository = eventRepository;
    }

    public PostalItem getByTrackNumber(String trackNumber) {
        return postalItemsRepository.findByTrackNumber(trackNumber);
    }

    @Transactional
    public PostalItem registrationNewPostalItem(PostalItem postalItem) {
        postalItem.setTrackNumber(PostalItemUtil.generateTrackNumber(postalItem));
        postalItem.setCreated(LocalDateTime.now());
        postalItem.setLastUpdated(LocalDateTime.now());

        PostalItem postalItemResult = postalItemsRepository.save(postalItem);

        Event registrationEvent = new Event();
        registrationEvent.setPostalItem(postalItemResult);
        registrationEvent.setType(EventType.REGISTRATION);
        registrationEvent.setCreated(LocalDateTime.now());

        Event eventResult = eventRepository.save(registrationEvent);
        postalItemResult.setEvents(List.of(eventResult));

        return postalItemResult;
    }
}
