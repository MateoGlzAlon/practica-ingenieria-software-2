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
        String voteJson = """
            {
              "commentId": 1,
              "userId": 1,
              "vote": 1
            }
        """;

        mockMvc.perform(post("/comments/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.vote", is(1)));
    }

    @Test
    void testGetVoteForCommentByUser() throws Exception {
        // Asume que se ha guardado previamente un voto (puede hacerse vía test setup o test DB)
        mockMvc.perform(get("/comments/vote/1/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.vote").isNumber());
    }
}
