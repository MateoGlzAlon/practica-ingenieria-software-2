package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.backend.controller.impl.CommentVoteControllerImpl;
import com.backend.controller.impl.GoogleAuthControllerImpl;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.CommentVoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class CommentVoteControllerImplTest {
    
     @Mock
    private CommentVoteService commentVoteService;

    @InjectMocks
    private CommentVoteControllerImpl commentVoteController;

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
    private PostImageEntity mockImage;
    private PostEntity mockPost;
    private PostOutputDTO mockPostOutput;
    private PostDetailsDTO mockPostDetails;
    private FeedPostDTO mockFeedPost;
    private GoogleLoginDTO mockLoginDTO;
    private CommentVoteEntity mockVote;



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

        mockImage = PostImageEntity.builder()
                .id(1L)
                .imageUrl("https://placehold.co/600x400?text=PostImage")
                .build();

        mockPostInput = PostInputDTO.builder()
                .title("Mock Post")
                .content("Content")
                .tagId(1L)
                .userId(1L)
                .build();

        mockPostOutput = PostOutputDTO.builder()
                .id(1L)
                .title("Mock Post")
                .content("Content")
                .build();

        mockPostDetails = PostDetailsDTO.builder()
                .id(1L)
                .title("Detailed Post")
                .build();

        mockFeedPost = FeedPostDTO.builder()
                .id(1L)
                .title("Feed Post")
                .build();

        mockLoginDTO = GoogleLoginDTO.builder()
                .email("user@example.com")
                .username("testuser")
                .avatarUrl("https://example.com/avatar.jpg")
                .build();

        mockVote = CommentVoteEntity.builder()
                .id(1L)
                .build();
    }

    @Test
    public void testFindCommentVoteById_ReturnsVote() {
        when(commentVoteService.findCommentVoteById(1L)).thenReturn(mockVote);

        CommentVoteEntity result = commentVoteController.findCommentVoteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(commentVoteService, times(1)).findCommentVoteById(1L);
    }

}
