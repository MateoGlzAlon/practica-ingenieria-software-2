package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import com.backend.controller.impl.CommentVoteControllerImpl;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
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

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private CommentVoteEntity mockVote;



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
