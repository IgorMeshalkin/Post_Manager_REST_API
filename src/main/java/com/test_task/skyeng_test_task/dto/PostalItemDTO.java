package com.test_task.skyeng_test_task.dto;

import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import com.test_task.skyeng_test_task.entities.entity_types.PostalItemType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PostalItemDTO {
    private Long id;
    private String trackNumber;
    private PostalItemType type;
    private Integer recipientIndex;
    private String recipientAddress;
    private String recipientName;
    private List<EventDTO> events;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    public static PostalItemDTO getFromPostalItem(PostalItem postalItem) {
        return new PostalItemDTO(
                postalItem.getId(),
                postalItem.getTrackNumber(),
                postalItem.getType(),
                postalItem.getRecipientIndex(),
                postalItem.getRecipientAddress(),
                postalItem.getRecipientName(),
                postalItem.getEvents()
                        .stream()
                        .map(EventDTO::getByEvent)
                        .sorted((a, b) -> {
                            if (a.getId() > b.getId()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        })
                        .collect(Collectors.toList()),
                postalItem.getCreated(),
                postalItem.getLastUpdated()
        );
    }
}
