package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.backend.controller.impl.PostControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.service.PostService;

public class PostControllerImplTest {
    @Mock
    private PostService postService;

    @InjectMocks
    private PostControllerImpl postController;

    private PostInputDTO mockPostInput;
    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private UserOutputDTO mockUserOutputDto;
    private PostEntity mockPost;
    private PostOutputDTO mockPostOutput;
    private PostDetailsDTO mockPostDetails;
    private FeedPostDTO mockFeedPost;

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

        mockPost = PostEntity.builder()
                .id(1L)
                .title("Post Title")
                .build();

        mockUserOutputDto = UserOutputDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .about("about user")
                .build();

        mockPostInput = PostInputDTO.builder()
                .title("Post Title")
                .content("Content")
                .tagId(1L)
                .userId(1L)
                .build();

        mockPostOutput = PostOutputDTO.builder()
                .id(1L)
                .title("Post Title")
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
    }

    @Test
    public void testFindPostById_ReturnsPostEntity() {
        when(postService.findPostById(1L)).thenReturn(mockPost);

        PostEntity result = postController.findPostById(1L);

        assertNotNull(result);
        assertEquals("Post Title", result.getTitle());
        verify(postService).findPostById(1L);
    }

    @Test
    public void testGetFeedPosts_ReturnsListOfFeedPostDTO() {
        List<String> tags = new ArrayList<>();
        tags.add("Java");

        when(postService.getFeedPosts(0, 10, 1L, tags)).thenReturn(List.of(mockFeedPost));


        List<FeedPostDTO> result = postController.getFeedPosts(0, 10, tags, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Feed Post", result.get(0).getTitle());
        verify(postService).getFeedPosts(0, 10, 1L, tags);
    }

    @Test
    public void testGetPostIndividual_ReturnsPostOutputDTO() {
        when(postService.getPostIndividual(1L)).thenReturn(mockPostOutput);

        PostOutputDTO result = postController.getPostIndividual(1L);

        assertNotNull(result);
        assertEquals("Post Title", result.getTitle());
        verify(postService).getPostIndividual(1L);
    }

    @Test
    public void testGetPostDetails_ReturnsPostDetailsDTO() {
        when(postService.getPostDetails(1L)).thenReturn(mockPostDetails);

        PostDetailsDTO result = postController.getPostDetails(1L);

        assertNotNull(result);
        assertEquals("Detailed Post", result.getTitle());
        verify(postService).getPostDetails(1L);
    }

    @Test
    public void testCreatePost_ReturnsCreatedPost() {
        when(postService.createPost(mockPostInput)).thenReturn(mockPost);

        PostEntity result = postController.createPost(mockPostInput);

        assertNotNull(result);
        assertEquals("Post Title", result.getTitle());
        verify(postService).createPost(mockPostInput);
    }
}