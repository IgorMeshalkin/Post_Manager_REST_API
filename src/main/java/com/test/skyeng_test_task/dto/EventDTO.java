package com.test.skyeng_test_task.dto;

import com.test.skyeng_test_task.entities.entities.Event;
import com.test.skyeng_test_task.entities.entity_types.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private EventType type;
    private PostOfficeDTO postOffice;
    private Boolean activeStatus;
    private LocalDateTime created;

    public static EventDTO getByEvent(Event event) {
        return new EventDTO(
                event.getId(),
                event.getType(),
                event.getPostOffice() != null ? PostOfficeDTO.getByPostOffice(event.getPostOffice()) : null,
                event.getActiveStatus(),
                event.getCreated()
        );
    }
}
