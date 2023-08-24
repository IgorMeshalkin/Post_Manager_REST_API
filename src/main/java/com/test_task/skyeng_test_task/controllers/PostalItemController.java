package com.test_task.skyeng_test_task.controllers;

import com.test_task.skyeng_test_task.dto.PostalItemDTO;
import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import com.test_task.skyeng_test_task.services.PostalItemService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "${description.get-postal-item}")
    public ResponseEntity<PostalItemDTO> getByTrackNumber(@RequestParam String trackNumber) {
        //Receives trackNumber and call PostalItemService to looking for PostalItem with this track number.
        PostalItem result = postalItemService.getByTrackNumber(trackNumber);
        if (result != null) {
            return new ResponseEntity<>(PostalItemDTO.getFromPostalItem(result), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "${description.registration-postal-item}")
    public ResponseEntity<PostalItemDTO> registrationNewPostalItem(@RequestBody PostalItem postalItem) {
        //Receives PostalItem object and call PostalItemService to registration this object in the system.
        PostalItem registeredPostalItem = postalItemService.registrationNewPostalItem(postalItem);
        PostalItemDTO result = PostalItemDTO.getFromPostalItem(registeredPostalItem);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
