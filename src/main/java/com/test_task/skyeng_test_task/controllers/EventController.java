package com.test_task.skyeng_test_task.controllers;

import com.test_task.skyeng_test_task.entities.entities.Event;
import com.test_task.skyeng_test_task.exceptions.IncorrectValuesException;
import com.test_task.skyeng_test_task.exceptions.PostItemAlreadyTakenException;
import com.test_task.skyeng_test_task.services.EventService;
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
        try {
            Event result = eventService.arrival(postOfficeId, postalItemId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (PostItemAlreadyTakenException | IncorrectValuesException customEx) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/leaving/{postOfficeId}/{postalItemId}")
    @Operation(summary = "${description.leaving-event}")
    public ResponseEntity<Event> leaving(@PathVariable Long postOfficeId, @PathVariable Long postalItemId) {
        try {
            Event result = eventService.leaving(postOfficeId, postalItemId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IncorrectValuesException icEx) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delivery/{postalItemId}")
    @Operation(summary = "${description.delivery-event}")
    public ResponseEntity<Event> delivery(@PathVariable Long postalItemId) {
        try {
            Event result = eventService.delivery(postalItemId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (PostItemAlreadyTakenException piEx) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
