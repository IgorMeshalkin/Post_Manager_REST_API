package com.test.skyeng_test_task.controllers;

import com.test.skyeng_test_task.entities.entities.Event;
import com.test.skyeng_test_task.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/arrival/{postOfficeId}/{postalItemId}")
    public ResponseEntity<Event> arrival(@PathVariable Long postOfficeId, @PathVariable Long postalItemId) {
        Event result = eventService.arrival(postOfficeId, postalItemId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/leaving/{postOfficeId}/{postalItemId}")
    public ResponseEntity<Event> leaving(@PathVariable Long postOfficeId, @PathVariable Long postalItemId) {
        Event result = eventService.leaving(postOfficeId, postalItemId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delivery/{postalItemId}")
    public ResponseEntity<Event> delivery(@PathVariable Long postalItemId) {
        Event result = eventService.delivery(postalItemId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
