package me.pyisoe.javatut.myapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyAppApiController.class)
class MyAppRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNameFromPostRequest() throws Exception {
        String json = """
            { "name": "Pyi Soe" }
        """;

        mockMvc.perform(
                        post("/api")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Pyi Soe"));
    }
}