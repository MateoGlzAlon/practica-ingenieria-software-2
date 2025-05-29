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

import com.backend.controller.impl.CommentControllerImpl;
import com.backend.controller.impl.CommentVoteControllerImpl;
import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.CommentService;
import com.backend.service.CommentVoteService;

public class CommentControllerImplTest {
    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentControllerImpl commentController;

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
    private CommentEntity mockComment;
    private CommentOutputDTO mockCommentOutput;
    private CommentInputDTO mockCommentInput;

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

        mockComment = CommentEntity.builder()
                .id(1L)
                .content("Test comment")
                .build();

        mockCommentOutput = CommentOutputDTO.builder()
                .id(1L)
                .content("Test comment output")
                .build();

        mockCommentInput = CommentInputDTO.builder()
                .content("New comment")
                .userId(1L)
                .postId(1L)
                .build();
    }

    @Test
    public void testFindCommentById_ReturnsCommentEntity() {
        when(commentService.findCommentById(1L)).thenReturn(mockComment);

        CommentEntity result = commentController.findCommentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test comment", result.getContent());
        verify(commentService, times(1)).findCommentById(1L);
    }

    @Test
    public void testFindCommentsOfAPost_ReturnsListOfDTOs_votes() {
        when(commentService.findCommentsOfAPost(1L, "votes")).thenReturn(List.of(mockCommentOutput));

        List<CommentOutputDTO> result = commentController.findCommentsOfAPost(1L, "votes");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test comment output", result.get(0).getContent());
        verify(commentService, times(1)).findCommentsOfAPost(1L, "votes");
    }

    public void testFindCommentsOfAPost_ReturnsListOfDTOs_newest() {
        when(commentService.findCommentsOfAPost(1L, "newest")).thenReturn(List.of(mockCommentOutput));

        List<CommentOutputDTO> result = commentController.findCommentsOfAPost(1L, "newest");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test comment output", result.get(0).getContent());
        verify(commentService, times(1)).findCommentsOfAPost(1L, "newest");
    }

    public void testFindCommentsOfAPost_ReturnsListOfDTOs_oldest() {
        when(commentService.findCommentsOfAPost(1L, "oldest")).thenReturn(List.of(mockCommentOutput));

        List<CommentOutputDTO> result = commentController.findCommentsOfAPost(1L, "oldest");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test comment output", result.get(0).getContent());
        verify(commentService, times(1)).findCommentsOfAPost(1L, "oldest");
    }

    @Test
    public void testCreateComment_ReturnsCreatedEntity() {
        when(commentService.createComment(mockCommentInput)).thenReturn(mockComment);

        CommentEntity result = commentController.createComment(mockCommentInput);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test comment", result.getContent());
        verify(commentService, times(1)).createComment(mockCommentInput);
    }
}
