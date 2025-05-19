package com.backend.servicetest;

import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.entity.*;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.repository.PostRepository;
import com.backend.repository.TagRepository;
import com.backend.repository.UserRepository;
import com.backend.service.impl.PostServiceImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PostServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;


    private static PostInputDTO mockPostInput;
    private static UserEntity mockUserEntity;
    private static TagEntity mockTagEntity;
    private static PostEntity mockPostEntity;

    @BeforeEach
    public void setupVariables() {
        MockitoAnnotations.openMocks(this);

        mockPostInput = PostInputDTO.builder()
                .title("Test Title")
                .content("Test Content")
                .userId(1L)
                .tagId(1L)
                .imageLinks(Arrays.asList(
                        "https://placehold.co/600x400?text=Post90",
                        "https://placehold.co/600x400?text=Post91"
                ))
                .build();

        mockUserEntity = UserEntity.builder()
                .id(1L)
                .name("Red User")
                .email("reduser@email.com")
                .role("USER")
                .build();

        mockTagEntity = TagEntity.builder()
                .id(1L)
                .name("Tag name")
                .posts(new HashSet<>())
                .build();

        mockPostEntity = PostEntity.builder()
                .id(1L)
                .title("Test Title")
                .content("Test Content")
                .user(mockUserEntity)
                .tag(mockTagEntity)
                .images(new ArrayList<>())
                .build();

    }

    @Test
    public void testCreatePost_Success() {
        // Arrange
        UserEntity user = mockUserEntity;
        TagEntity tag = mockTagEntity;
        PostEntity savedPost = mockPostEntity;

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(postRepository.save(any(PostEntity.class))).thenReturn(savedPost);

        // Act
        PostEntity result = postService.createPost(mockPostInput);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(PostEntity.class));
    }

}
