package com.backend.servicetest;

import com.backend.persistence.entity.*;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.repository.*;
import com.backend.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostImageRepository postImageRepository;
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
    private PostServiceImpl postService;

    private PostInputDTO mockPostInput;
    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;

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

    }

    @Test
    public void testCreatePost_Success() {
        // Preparar una versión controlada del PostEntity que se devuelve tras guardar
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(mockTagEntity));
        when(postRepository.save(any(PostEntity.class))).thenAnswer(invocation -> {
            PostEntity postArg = invocation.getArgument(0);
            postArg.setId(1L);
            return postArg;
        });

        when(postImageRepository.save(any(PostImageEntity.class))).thenAnswer(invocation -> {
            PostImageEntity image = invocation.getArgument(0);
            image.setId(1L);
            return image;
        });

        PostEntity result = postService.createPost(mockPostInput);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        assertEquals(1, result.getImages().size()); // Validar que una imagen fue agregada
        assertEquals("https://placehold.co/600x400?text=Post90", result.getImages().get(0).getImageUrl());

        verify(userRepository).findById(1L);
        verify(tagRepository).findById(1L);
        verify(postRepository).save(any(PostEntity.class));
        verify(postImageRepository, times(1)).save(any(PostImageEntity.class));
    }


    @Test
    public void testCreatePost_Success_ImagesNull() {
        PostInputDTO inputWithoutImages = PostInputDTO.builder()
                .title("Test Title")
                .content("Test Content")
                .tagId(1L)
                .userId(1L)
                .imageLinks(null)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(mockTagEntity));
        when(postRepository.save(any(PostEntity.class))).thenAnswer(invocation -> {
            PostEntity postArg = invocation.getArgument(0);
            postArg.setId(1L);
            return postArg;
        });

        PostEntity result = postService.createPost(inputWithoutImages);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        assertEquals(0, result.getImages().size());

        verify(userRepository).findById(1L);
        verify(tagRepository).findById(1L);
        verify(postRepository).save(any(PostEntity.class));
    }


    @Test
    public void testCreatePost_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(tagRepository.findById(1L)).thenReturn(Optional.of(mockTagEntity));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            postService.createPost(mockPostInput);
        });
        assertEquals("User not found", thrown.getMessage());
    }


    @Test
    public void testFindPostById_ReturnsPost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPostEntity));
        PostEntity result = postService.findPostById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindPostById_ReturnsNullIfNotFound() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());
        assertNull(postService.findPostById(999L));
    }

    @Test
    void testUserFoundById_OrElseThrow() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));

        UserEntity user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testUserNotFoundById_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
        });

        assertEquals("User not found", thrown.getMessage());
    }


    @Test
    public void testGetPostIndividual_ReturnsDTO() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPostEntity));
        var dto = postService.getPostIndividual(1L);
        assertNotNull(dto);
        assertEquals("Test Title", dto.getTitle());
    }

    @Test
    public void testGetPostIndividual_ReturnsNullIfNotFound() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());
        assertNull(postService.getPostIndividual(999L));
    }

    @Test
    public void testGetPostDetails_ReturnsCorrectData() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPostEntity));

        PostDetailsDTO details = postService.getPostDetails(1L);
        assertNotNull(details);
        assertEquals("Test Title", details.getTitle());
        assertEquals(1, details.getPostImages().size());
    }

    @Test
    public void testGetFeedPosts_ReturnsList() {
        List<PostEntity> postList = Arrays.asList(mockPostEntity);
        Page<PostEntity> mockPage = mock(Page.class);

        when(mockPage.getContent()).thenReturn(postList);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        List<FeedPostDTO> feed = postService.getFeedPosts(0, 10, 1L);

        assertNotNull(feed);
        assertEquals(1, feed.size());
        assertEquals("Test Title", feed.get(0).getTitle());
    }

    @Test
    void testGetFeedPosts_NullImages() {
        PostEntity post = PostEntity.builder()
                .id(1L)
                .images(null)
                .comments(new ArrayList<>())
                .createdAt(new Date())
                .build();

        assertNull(post.getImages());
    }

    @Test
    public void testCreatePost_ImageLinksEmpty_DoesNotAddImages() {
        PostInputDTO postInputWithoutImages = PostInputDTO.builder()
                .title("No Image Post")
                .content("Content without images")
                .tagId(1L)
                .userId(1L)
                .imageLinks(Collections.emptyList())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(mockTagEntity));
        when(postRepository.save(any(PostEntity.class))).thenAnswer(invocation -> {
            PostEntity savedPost = invocation.getArgument(0);
            assertNotNull(savedPost.getImages());
            assertTrue(savedPost.getImages().isEmpty());
            return savedPost;
        });

        PostEntity result = postService.createPost(postInputWithoutImages);
        assertNotNull(result);
    }

    @Test
    public void testCreatePost_ImageLinksIsNull_DoesNotFail() {
        PostInputDTO postInputWithoutImages = PostInputDTO.builder()
                .title("No Image Post")
                .content("Content without images")
                .tagId(1L)
                .userId(1L)
                .imageLinks(null)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(mockTagEntity));
        when(postRepository.save(any(PostEntity.class))).thenAnswer(invocation -> {
            PostEntity savedPost = invocation.getArgument(0);
            assertNotNull(savedPost.getImages());
            assertTrue(savedPost.getImages().isEmpty());
            return savedPost;
        });

        PostEntity result = postService.createPost(postInputWithoutImages);
        assertNotNull(result);
    }

    @Test
    public void testGetFeedPosts_PostWithNullImages() {
        PostEntity postWithNullImages = PostEntity.builder()
                .id(2L)
                .title("No Images")
                .images(null)
                .user(mockUserEntity)
                .content("Short content")
                .comments(new ArrayList<>())
                .createdAt(new Date())
                .votes(0)
                .state("open")
                .build();

        Page<PostEntity> mockPage = mock(Page.class);
        when(mockPage.getContent()).thenReturn(List.of(postWithNullImages));
        when(postRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        List<FeedPostDTO> result = postService.getFeedPosts(0, 10, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getImageURL());
    }

    @Test
    public void testGetFeedPosts_PostWithEmptyImages() {
        PostEntity postWithEmptyImages = PostEntity.builder()
                .id(3L)
                .title("No Images Again")
                .images(new ArrayList<>())
                .user(mockUserEntity)
                .content("Another short content")
                .comments(new ArrayList<>())
                .createdAt(new Date())
                .votes(0)
                .state("open")
                .build();

        Page<PostEntity> mockPage = mock(Page.class);
        when(mockPage.getContent()).thenReturn(List.of(postWithEmptyImages));
        when(postRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        List<FeedPostDTO> result = postService.getFeedPosts(0, 10, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getImageURL());
    }


}