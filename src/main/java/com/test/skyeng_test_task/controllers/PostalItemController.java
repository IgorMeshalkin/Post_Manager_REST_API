package com.test.skyeng_test_task.controllers;

import com.test.skyeng_test_task.dto.PostalItemDTO;
import com.test.skyeng_test_task.entities.entities.PostalItem;
import com.test.skyeng_test_task.services.PostalItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postal_items")
public class PostalItemController {
    private final PostalItemService postalItemService;

    public PostalItemController(PostalItemService postalItemService) {
        this.postalItemService = postalItemService;
    }

    @GetMapping
    public ResponseEntity<PostalItemDTO> getByTrackNumber(@RequestParam String trackNumber) {
        PostalItem result = postalItemService.getByTrackNumber(trackNumber);
        if (result != null) {
            return new ResponseEntity<>(PostalItemDTO.getFromPostalItem(result), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PostalItemDTO> registrationNewPostalItem(@RequestBody PostalItem postalItem) {
        PostalItem registeredPostalItem = postalItemService.registrationNewPostalItem(postalItem);
        PostalItemDTO result = PostalItemDTO.getFromPostalItem(registeredPostalItem);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
