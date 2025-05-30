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
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockVote = PostVoteEntity.builder()
                .id(1L)
                .post(null)  
                .user(null)  
                .build();
    }

    @Test
    void testFindPostById_ReturnsPostVote() {
        when(postVoteRepository.findById(1L)).thenReturn(Optional.of(mockVote));

        PostVoteEntity result = postVoteService.findPostVoteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(postVoteRepository).findById(1L);
    }

    @Test
    void testFindPostById_ReturnsNullWhenNotFound() {
        when(postVoteRepository.findById(2L)).thenReturn(Optional.empty());

        PostVoteEntity result = postVoteService.findPostVoteById(2L);

        assertNull(result);
        verify(postVoteRepository).findById(2L);
    }

    @Test
    void testServiceConstructor() {
        PostVoteServiceImpl service = new PostVoteServiceImpl(postVoteRepository, null, null, null);
        assertNotNull(service);
    }
}
