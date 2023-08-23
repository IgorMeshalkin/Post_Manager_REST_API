package com.test_task.skyeng_test_task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EventControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void arrival_correct() throws Exception {
        //Test adding event 'ARRIVED_AT_THE_POST_OFFICE' with correct values.
        mockMvc.perform(post("/api/events/arrival/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.type").value("ARRIVED_AT_THE_POST_OFFICE"))
                .andExpect(jsonPath("$.postOffice.id").value(1));
    }

    @Test
    void arrival_duplicate_values() throws Exception {
        //Test adding event 'ARRIVED_AT_THE_POST_OFFICE' when this post office and postal item already have active event 'ARRIVED_AT_THE_POST_OFFICE'.
        mockMvc.perform(post("/api/events/arrival/3/3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void leaving_correct() throws Exception {
        //Test adding event 'LEFT_POST_OFFICE' with correct values.
        mockMvc.perform(post("/api/events/leaving/3/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.type").value("LEFT_POST_OFFICE"))
                .andExpect(jsonPath("$.postOffice.id").value(3));

        //Check last event 'ARRIVED_AT_THE_POST_OFFICE' of this post office and postal item changed active status to false.
        mockMvc.perform(get("/api/postal_items?trackNumber=PC23081713211822"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events[1].activeStatus").value(false));
    }

    @Test
    void leaving_without_last_arrival() throws Exception {
        //Test adding event 'LEFT_POST_OFFICE' with values that don't have last 'ARRIVED_AT_THE_POST_OFFICE' event.
        mockMvc.perform(post("/api/events/leaving/1/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delivery_correct() throws Exception {
        //Test adding event 'DELIVERED' with correct values.
        mockMvc.perform(post("/api/events/delivery/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("DELIVERED"));
    }

    @Test
    void delivery_active_arrived_event() throws Exception {
        //Test adding event 'DELIVERED' with post item that have active 'ARRIVED_AT_THE_POST_OFFICE' event.
        mockMvc.perform(post("/api/events/delivery/3"))
                .andExpect(status().isBadRequest());
    }
}
