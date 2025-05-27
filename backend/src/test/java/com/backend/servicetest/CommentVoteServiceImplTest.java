package com.backend.servicetest;

import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.repository.CommentVoteRepository;
import com.backend.service.impl.CommentVoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentVoteServiceImplTest {

    @Mock
    private CommentVoteRepository commentVoteRepository;

    @InjectMocks
    private CommentVoteServiceImpl commentVoteService;

    private CommentVoteEntity mockVote;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockVote = CommentVoteEntity.builder()
                .id(1L)
                .build();
    }

    @Test
    void testFindCommentById_ReturnsVote() {
        when(commentVoteRepository.findById(1L)).thenReturn(Optional.of(mockVote));

        CommentVoteEntity result = commentVoteService.findCommentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(commentVoteRepository).findById(1L);
    }

    @Test
    void testFindCommentById_NotFound_ReturnsNull() {
        when(commentVoteRepository.findById(99L)).thenReturn(Optional.empty());

        CommentVoteEntity result = commentVoteService.findCommentById(99L);

        assertNull(result);
        verify(commentVoteRepository).findById(99L);
    }
}
