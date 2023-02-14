package com.example.lecture9_10_11.controller;

import com.example.lecture9_10_11.Lecture91011Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Lecture91011Application.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // JUnit test for Get All categories REST API
    @Test
    @Sql(scripts = "/init.sql")
    public void shouldGetAllCategories() throws Exception {
        ResultActions resp = mockMvc.perform(get("/api/categories"));

        resp.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(4));

    }
}