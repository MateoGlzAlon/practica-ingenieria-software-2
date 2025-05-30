package com.backend.servicetest;

import com.backend.persistence.entity.PostVoteEntity;
import com.backend.repository.PostVoteRepository;
import com.backend.service.impl.PostVoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostVoteServiceImplTest {

    @Mock
    private PostVoteRepository postVoteRepository;

    @InjectMocks
    private PostVoteServiceImpl postVoteService;

    private PostVoteEntity mockVote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockVote = PostVoteEntity.builder()
                .id(1L)
                .post(null)  
                .user(null)  
                .build();
    }

/*************  ✨ Windsurf Command ⭐  *************/
/**
 * Test that verifies the PostVoteServiceImpl's findPostVoteById method
 * returns a PostVoteEntity when a valid ID is provided and exists in the repository.
 * It mocks the repository to return a predefined PostVoteEntity and checks
 * that the returned entity is not null and has the expected ID.
 */

/*******  3faab409-1ab9-44ad-b026-880a31fc88d1  *******/
    @Test
    public void testFindPostById_ReturnsPostVote() {
        when(postVoteRepository.findById(1L)).thenReturn(Optional.of(mockVote));

        PostVoteEntity result = postVoteService.findPostVoteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(postVoteRepository).findById(1L);
    }

    @Test
    public void testFindPostById_ReturnsNullWhenNotFound() {
        when(postVoteRepository.findById(2L)).thenReturn(Optional.empty());

        PostVoteEntity result = postVoteService.findPostVoteById(2L);

        assertNull(result);
        verify(postVoteRepository).findById(2L);
    }
}
