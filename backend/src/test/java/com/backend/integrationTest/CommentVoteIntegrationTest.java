package com.backend.integrationTest;

import com.backend.repository.CommentVoteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class CommentVoteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentVoteRepository commentVoteRepository;

    @Test
    void testVoteOnComment() throws Exception {

        mockMvc.perform(get("/commentvotes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment.id", is(1)))
                .andExpect(jsonPath("$.user.id", is(3)))
                .andExpect(jsonPath("$.id", is(1)));
    }

}
