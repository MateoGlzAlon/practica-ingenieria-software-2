package com.backend.servicetest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import com.backend.persistence.entity.TipEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.CommentVoteRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.PostVoteRepository;
import com.backend.repository.TagRepository;
import com.backend.repository.TipRepository;
import com.backend.repository.UserRepository;
import com.backend.service.impl.PostServiceImpl;
import com.backend.service.impl.TipServiceImpl;

public class TipServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private TipRepository tipRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostVoteRepository postVoteRepository;
    @Mock
    private CommentVoteRepository commentVoteRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TipServiceImpl tipService;

    private PostInputDTO mockPostInput;
    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private TipEntity mockTipEntity;

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

        mockTipEntity = TipEntity.builder()
                .id(1L)
                .post(mockPostEntity)
                .amount(100)
                .createdAt(new Date())
                .build();
    }

    @Test
    public void testFindTipById_TipExists() {
        when(tipRepository.findById(1L)).thenReturn(Optional.of(mockTipEntity));

        TipEntity result = tipService.findTipById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tipRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindTipById_TipDoesNotExist() {
        when(tipRepository.findById(99L)).thenReturn(Optional.empty());

        TipEntity result = tipService.findTipById(99L);

        assertNull(result);
        verify(tipRepository, times(1)).findById(99L);
    }
}