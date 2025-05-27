package com.backend.servicetest;

import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.UserRepository;
import com.backend.service.impl.StatsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private StatsServiceImpl statsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCommunityStats_ReturnsCorrectCounts() {
        when(userRepository.count()).thenReturn(10L);
        when(commentRepository.count()).thenReturn(50L);
        when(postRepository.count()).thenReturn(20L);

        CommunityStatsDTO stats = statsService.getCommunityStats();

        assertNotNull(stats);
        assertEquals(10L, stats.getUsers());
        assertEquals(50L, stats.getAnswers());
        assertEquals(20L, stats.getQuestions());

        verify(userRepository).count();
        verify(commentRepository).count();
        verify(postRepository).count();
    }
}
