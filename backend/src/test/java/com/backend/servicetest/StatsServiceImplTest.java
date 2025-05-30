package com.backend.servicetest;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.PostHotQuestionsDTO;
import com.backend.persistence.specialdto.UserBestStatsDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.UserRepository;
import com.backend.service.impl.StatsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void testGetTop3Users_ReturnsListOfUsers() {
        Object[] row1 = new Object[]{1L, "user1", 15L};
        Object[] row2 = new Object[]{2L, "user2", 10L};
        Object[] row3 = new Object[]{3L, "user3", 5L};

        when(userRepository.findTopUsersByActivity(any(Pageable.class)))
                .thenReturn(Arrays.asList(row1, row2, row3));

        List<UserBestStatsDTO> topUsers = statsService.getTop3Users();

        assertEquals(3, topUsers.size());
        assertEquals("user1", topUsers.get(0).getUsername());
        assertEquals(15L, topUsers.get(0).getTotalContributions());
    }

    @Test
    public void testHotPosts_ReturnsRecentPosts() {
        PostEntity post1 = PostEntity.builder().id(1L).title("Post One").build();
        PostEntity post2 = PostEntity.builder().id(2L).title("Post Two").build();
        PostEntity post3 = PostEntity.builder().id(3L).title("Post Three").build();

        List<PostEntity> posts = Arrays.asList(post1, post2, post3);
        Page<PostEntity> page = new PageImpl<>(posts);

        when(postRepository.findAll(any(Pageable.class))).thenReturn(page);

        List<PostHotQuestionsDTO> result = statsService.hotPosts();

        assertEquals(3, result.size());
        assertEquals("Post One", result.get(0).getTitle());
        assertEquals(1L, result.get(0).getId());
    }
}
