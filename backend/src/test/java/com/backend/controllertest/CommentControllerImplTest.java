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
import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.service.CommentService;

public class CommentControllerImplTest {
    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentControllerImpl commentController;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private CommentEntity mockComment;
    private CommentOutputDTO mockCommentOutput;
    private CommentInputDTO mockCommentInput;

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

        PostEntity post = PostEntity.builder()
                .id(1L)
                .title("Post Title")
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

    @Test
    public void testFindCommentsOfAPost_ReturnsListOfDTOs_newest() {
        when(commentService.findCommentsOfAPost(1L, "newest")).thenReturn(List.of(mockCommentOutput));

        List<CommentOutputDTO> result = commentController.findCommentsOfAPost(1L, "newest");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test comment output", result.get(0).getContent());
        verify(commentService, times(1)).findCommentsOfAPost(1L, "newest");
    }

    @Test
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
