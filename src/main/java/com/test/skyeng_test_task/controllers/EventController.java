package com.test.skyeng_test_task.controllers;

import com.test.skyeng_test_task.entities.entities.Event;
import com.test.skyeng_test_task.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/arrival/{postOfficeId}/{postalItemId}")
    @Operation(summary = "${description.arrival-event}")
    public ResponseEntity<Event> arrival(@PathVariable Long postOfficeId, @PathVariable Long postalItemId) {
        Event result = eventService.arrival(postOfficeId, postalItemId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/leaving/{postOfficeId}/{postalItemId}")
    @Operation(summary = "${description.leaving-event}")
    public ResponseEntity<Event> leaving(@PathVariable Long postOfficeId, @PathVariable Long postalItemId) {
        Event result = eventService.leaving(postOfficeId, postalItemId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delivery/{postalItemId}")
    @Operation(summary = "${description.delivery-event}")
    public ResponseEntity<Event> delivery(@PathVariable Long postalItemId) {
        Event result = eventService.delivery(postalItemId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
