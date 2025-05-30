package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.controller.impl.StatsControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.PostHotQuestionsDTO;
import com.backend.persistence.specialdto.UserBestStatsDTO;
import com.backend.service.impl.StatsServiceImpl;

public class StatsControllerImplTest {
    @Mock
    private StatsServiceImpl statsService;

    @InjectMocks    
    private StatsControllerImpl statsController;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private UserOutputDTO mockUserOutputDto;
    private CommunityStatsDTO mockStats;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockUserEntity = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .build();

        mockTagEntity = TagEntity.builder()
                .id(1L)
                .name("Java")
                .posts(new HashSet<>())
                .build();

        mockPostImageEntity = PostImageEntity.builder()
                .id(1L)
                .imageUrl("https://placehold.co/600x400?text=Post90")
                .post(mockPostEntity)
                .build();

        mockPostEntity = PostEntity.builder()
                .id(1L)
                .title("Test Title")
                .content("Test Content")
                .user(mockUserEntity)
                .tag(mockTagEntity)
                .votes(5)
                .images(Arrays.asList(mockPostImageEntity))
                .comments(new ArrayList<>())
                .state("open")
                .createdAt(new Date())
                .build();

        mockUserOutputDto = UserOutputDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .about("about user")
                .build();

        mockStats = CommunityStatsDTO.builder()
                .users(50L)
                .questions(200L)
                .answers (300L)
                .build();
    }

    @Test
    public void testGetCommunityStats_ReturnsStats() {
        when(statsService.getCommunityStats()).thenReturn(mockStats);

        CommunityStatsDTO result = statsController.getCommunityStats();

        assertNotNull(result);
        assertEquals(50L, result.getUsers());
        assertEquals(200L, result.getQuestions());
        assertEquals(300L, result.getAnswers());

        verify(statsService, times(1)).getCommunityStats();
    }

    @Test
    public void testGetTop3Users_ReturnsList() {
        List<UserBestStatsDTO> topUsers = List.of(
                UserBestStatsDTO.builder().id(1L).username("Alice").totalContributions(100L).build(),
                UserBestStatsDTO.builder().id(2L).username("Bob").totalContributions(90L).build(),
                UserBestStatsDTO.builder().id(3L).username("Charlie").totalContributions(80L).build()
        );

        when(statsService.getTop3Users()).thenReturn(topUsers);

        List<UserBestStatsDTO> result = statsController.getTop3Users();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getUsername());

        verify(statsService, times(1)).getTop3Users();
    }

    @Test
    public void testHotPosts_ReturnsList() {
        List<PostHotQuestionsDTO> hotPosts = List.of(
                PostHotQuestionsDTO.builder().id(1L).title("Hot Question 1").build(),
                PostHotQuestionsDTO.builder().id(2L).title("Hot Question 2").build()
        );

        when(statsService.hotPosts()).thenReturn(hotPosts);

        List<PostHotQuestionsDTO> result = statsController.hotPosts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hot Question 1", result.get(0).getTitle());

        verify(statsService, times(1)).hotPosts();
    }
}
