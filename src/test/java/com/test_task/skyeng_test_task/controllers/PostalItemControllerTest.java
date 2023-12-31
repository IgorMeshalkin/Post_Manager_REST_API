package com.test_task.skyeng_test_task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test_task.skyeng_test_task.test_util.PostalItemTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class PostalItemControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getByTrackNumber_correct_value() throws Exception {
        //Test getting PostalItem by correct track number.
        mockMvc.perform(get("/api/postal_items?trackNumber=PP23081613224633"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipientName").value("Сергеев Сергей"))
                .andExpect(jsonPath("$.events.length()").value(3));
    }

    @Test
    void getByTrackNumber_incorrect_value() throws Exception {
        //Test getting PostalItem by incorrect track number.
        mockMvc.perform(get("/api/postal_items?trackNumber=PP23081613224636"))
                .andExpect(status().isNotFound());
    }

    @Test
    void registrationNewPostalItem() throws Exception {
        //Test registration new PostalItem.
        mockMvc.perform(post("/api/postal_items")
                        .content(objectMapper.writeValueAsString(PostalItemTestUtil.getPostalItemForControllerRegistrationTest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.recipientName").value("Имя получателя тестового отправления"))
                .andExpect(jsonPath("$.recipientAddress").value("Адрес тестового отправления"))
                .andExpect(jsonPath("$.events.length()").value(1));
    }
}
