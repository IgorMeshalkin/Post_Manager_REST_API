package com.test_task.skyeng_test_task.dto;

import com.test_task.skyeng_test_task.entities.entities.PostOffice;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostOfficeDTO {
    private Long id;
    private Integer postIndex;
    private String name;
    private String address;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    public static PostOfficeDTO getByPostOffice(PostOffice postOffice) {
        return new PostOfficeDTO(
                postOffice.getId(),
                postOffice.getPostIndex(),
                postOffice.getName(),
                postOffice.getAddress(),
                postOffice.getCreated(),
                postOffice.getLastUpdated()
        );
    }
}
