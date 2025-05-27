package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.backend.controller.impl.PostVoteControllerImpl;
import com.backend.controller.impl.StatsControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.inputDTO.PostVoteInputDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.PostVoteService;
import com.backend.service.impl.StatsServiceImpl;

public class PostVoteControllerImplTest {
    @Mock
    private PostVoteService postVoteService;

    @InjectMocks
    private PostVoteControllerImpl postVoteController;

    private PostInputDTO mockPostInput;
    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private TagOutputDTO mockTagOutputDTO;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private TipEntity mockTipEntity;
    private UserInputDTO mockUserInputDto;
    private ProfileDTO mockProfileDto;
    private UserOutputDTO mockUserOutputDto;
    private CommunityStatsDTO mockStats;
    private PostVoteEntity mockVote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockPostInput = PostInputDTO.builder()
                .title("Test Title")
                .content("Test Content")
                .tagId(1L)
                .userId(1L)
                .imageLinks(Arrays.asList("https://placehold.co/600x400?text=Post90"))
                .build();

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

        mockTagOutputDTO = TagOutputDTO.builder()
                .tags(List.of("mockTag"))
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

        PostEntity post = PostEntity.builder()
                .id(1L)
                .title("Post Title")
                .build();

        mockTipEntity = TipEntity.builder()
                .id(1L)
                .amount(100)
                .post(post)
                .createdAt(new Date())
                .build();

        
        mockUserInputDto = UserInputDTO.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .about("about user")
                .build();

        mockUserOutputDto = UserOutputDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .about("about user")
                .build();
        mockProfileDto = ProfileDTO.builder()
                .user(mockUserOutputDto)
                .build();

        mockStats = CommunityStatsDTO.builder()
                .users(50L)
                .questions(200L)
                .answers (300L)
                .build();
        
        mockVote = PostVoteEntity.builder().id(1L).build();

    }

    @Test
    public void testFindPostVoteById_ReturnsPostVoteEntity() {
        when(postVoteService.findPostVoteById(1L)).thenReturn(mockVote);

        PostVoteEntity result = postVoteController.findPostVoteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(postVoteService, times(1)).findPostVoteById(1L);
    }

    @Test
    public void testCreatePostVote_ReturnsCreatedEntity() {
        PostVoteInputDTO inputDTO = PostVoteInputDTO.builder()
                .postId(10L)
                .userId(5L)
                .build();

        PostVoteEntity createdVote = PostVoteEntity.builder().id(1L).build();

        when(postVoteService.createPostVote(inputDTO)).thenReturn(createdVote);

        PostVoteEntity result = postVoteController.createPostVote(inputDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(postVoteService, times(1)).createPostVote(inputDTO);
    }

    @Test
    public void testIsPostVoted_ReturnsTrue() {
        Long userId = 2L;
        Long postId = 3L;

        when(postVoteService.isPostVoted(userId, postId)).thenReturn(true);

        boolean result = postVoteController.isPostVoted(userId, postId);

        assertTrue(result);
        verify(postVoteService, times(1)).isPostVoted(userId, postId);
    }

    @Test
    public void testIsPostVoted_ReturnsFalse() {
        Long userId = 2L;
        Long postId = 3L;

        when(postVoteService.isPostVoted(userId, postId)).thenReturn(false);

        boolean result = postVoteController.isPostVoted(userId, postId);

        assertFalse(result);
        verify(postVoteService, times(1)).isPostVoted(userId, postId);
    }

}
