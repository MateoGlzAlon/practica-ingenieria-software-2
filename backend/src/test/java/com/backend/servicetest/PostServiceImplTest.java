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
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PostRepository postRepository;
    @Mock private TipRepository tipRepository;
    @Mock private CommentRepository commentRepository;
    @Mock private PostVoteRepository postVoteRepository;
    @Mock private CommentVoteRepository commentVoteRepository;
    @Mock private TagRepository tagRepository;
    @Mock private PostImageRepository postImageRepository;

    @InjectMocks private PostServiceImpl postService;

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

        mockPostEntity = PostEntity.builder()
                .id(1L)
                .title("Test Title")
                .content("Test Content")
                .user(mockUserEntity)
                .tag(mockTagEntity)
                .votes(5)
                .images(new ArrayList<>())
                .comments(new ArrayList<>())
                .state("open")
                .createdAt(new Date())
                .build();

        mockPostImageEntity = PostImageEntity.builder()
                .id(1L)
                .imageUrl("https://placehold.co/600x400?text=Post90")
                .post(mockPostEntity)
                .build();
    }

    @Test
    public void testCreatePost_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(mockTagEntity));
        when(postRepository.save(any(PostEntity.class))).thenReturn(mockPostEntity);

        PostEntity result = postService.createPost(mockPostInput);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(userRepository).findById(1L);
        verify(tagRepository).findById(1L);
        verify(postRepository).save(any(PostEntity.class));
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
        mockPostEntity.getImages().add(mockPostImageEntity);
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

        List<FeedPostDTO> feed = postService.getFeedPosts(0, 10);

        assertNotNull(feed);
        assertEquals(1, feed.size());
        assertEquals("Test Title", feed.get(0).getTitle());
    }
}