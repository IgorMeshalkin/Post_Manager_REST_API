package com.test_task.skyeng_test_task.service;

import com.test_task.skyeng_test_task.entities.entities.PostOffice;
import com.test_task.skyeng_test_task.exceptions.IncorrectValuesException;
import com.test_task.skyeng_test_task.exceptions.PostItemAlreadyTakenException;
import com.test_task.skyeng_test_task.repositories.EventRepository;
import com.test_task.skyeng_test_task.repositories.PostOfficeRepository;
import com.test_task.skyeng_test_task.repositories.PostalItemRepository;
import com.test_task.skyeng_test_task.services.EventService;
import com.test_task.skyeng_test_task.test_util.EventTestUtil;
import com.test_task.skyeng_test_task.test_util.PostOfficeTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @InjectMocks
    private EventService eventService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private PostOfficeRepository postOfficeRepository;
    @Mock
    private PostalItemRepository postalItemRepository;

    @Test
    void arrival_incorrect_values() {
        //Test that EventService.arrival() method throws IncorrectValuesException if received PostOffice or PostalItem isn't found in the database.
        PostOffice postOfficeFromDB = PostOfficeTestUtil.getPostOfficeForServiceArrivalMethod();

        Mockito.lenient().when(postOfficeRepository.findById(1L))
                .thenReturn(Optional.of(postOfficeFromDB));
        Mockito.lenient().when(postalItemRepository.findById(1L))
                .thenReturn(Optional.empty());
        Mockito.lenient().when(eventRepository.findArrivaledActiveEvent(1L))
                .thenReturn(null);
        Mockito.lenient().when(eventRepository.findDeliveredEvent(1L))
                .thenReturn(null);
        Mockito.lenient().when(eventRepository.save(EventTestUtil.getBeforeArrivedEvent(postOfficeFromDB, null)))
                .thenReturn(EventTestUtil.getAfterArrivedEvent());

        Assertions.assertThrows(IncorrectValuesException.class, () -> eventService.arrival(1L, 1L));
    }

    @Test
    void arrival_already_delivered() {
        //Test that EventService.arrival() method throws PostItemAlreadyTakenException if received PostalItem is already delivered.
        Mockito.lenient().when(eventRepository.findArrivaledActiveEvent(1L))
                .thenReturn(null);
        Mockito.lenient().when(eventRepository.findDeliveredEvent(1L))
                .thenReturn(EventTestUtil.getAlreadyDeliveredEvent());

        Assertions.assertThrows(PostItemAlreadyTakenException.class, () -> eventService.arrival(1L, 1L));
    }

    @Test
    void leaving_incorrect_values() {
        //Test that EventService.leaving() method throws IncorrectValuesException if this passed PostItem don't have active 'ARRIVED_AT_THE_POST_OFFICE' event.
        Mockito.lenient().when(eventRepository.findArrivaledActiveEvent(1L))
                .thenReturn(null);

        Assertions.assertThrows(IncorrectValuesException.class, () -> eventService.leaving(1L, 1L));
    }

    @Test
    void delivery_do_not_left() throws IncorrectValuesException {
        //Test that EventService.delivery() method throws PostItemAlreadyTakenException if this passed PostItem have active 'ARRIVED_AT_THE_POST_OFFICE' event.
        Mockito.lenient().when(eventRepository.findArrivaledActiveEvent(1L))
                .thenReturn(EventTestUtil.getAfterArrivedEvent());

        Assertions.assertThrows(PostItemAlreadyTakenException.class, () -> eventService.delivery(1L));
    }

    @Test
    void delivery_already_delivered() throws IncorrectValuesException {
        //Test that EventService.delivery() method throws PostItemAlreadyTakenException if this passed PostItem is already delivered.
        Mockito.lenient().when(eventRepository.findDeliveredEvent(1L))
                .thenReturn(EventTestUtil.getAlreadyDeliveredEvent());

        Assertions.assertThrows(PostItemAlreadyTakenException.class, () -> eventService.delivery(1L));
    }
}
