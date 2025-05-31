package com.backend.servicetest;

import com.backend.persistence.entity.*;
import com.backend.persistence.inputDTO.*;
import com.backend.repository.*;
import com.backend.service.impl.PostVoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostVoteServiceImplTest {

    @Mock private PostVoteRepository postVoteRepository;
    @Mock private UserRepository userRepository;
    @Mock private PostRepository postRepository;
    @Mock private CommentRepository commentRepository;

    @InjectMocks
    private PostVoteServiceImpl postVoteService;

    private PostVoteEntity mockVote;

    private UserEntity mockUserEntityEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private UserEntity mockUserEntity;
    private PostVoteInputDTO mockPostVoteInputDto;




    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockUserEntity = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .build();


        mockUserEntityEntity = UserEntity.builder()
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
                .user(mockUserEntityEntity)
                .tag(mockTagEntity)
                .votes(5)
                .images(Arrays.asList(mockPostImageEntity))
                .comments(new ArrayList<>())
                .state("open")
                .createdAt(new Date())
                .build();

        mockVote = PostVoteEntity.builder()
                .id(1L)
                .post(null)
                .user(null)
                .build();

        mockPostVoteInputDto  = PostVoteInputDTO.builder()
                .postId(10L)
                .userId(1L)
                .build();
    }

    @Test
    void testFindPostById_ReturnsPostVote() {
        when(postVoteRepository.findById(1L)).thenReturn(Optional.of(mockVote));

        PostVoteEntity result = postVoteService.findPostVoteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(postVoteRepository).findById(1L);
    }

    @Test
    void testFindPostById_ReturnsNullWhenNotFound() {
        when(postVoteRepository.findById(2L)).thenReturn(Optional.empty());

        PostVoteEntity result = postVoteService.findPostVoteById(2L);

        assertNull(result);
        verify(postVoteRepository).findById(2L);
    }



    @Test
    void testCreatePostVote_WhenAlreadyVoted_DeletesVoteAndDecreases() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(true);
        when(postRepository.findById(10L)).thenReturn(Optional.of(mockPostEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));

        PostVoteEntity result = postVoteService.createPostVote(mockPostVoteInputDto);

        assertNull(result);
        verify(postVoteRepository).deleteByUserIDProjectId(1L, 10L);
        verify(postRepository).save(mockPostEntity);
    }

    @Test
    void testCreatePostVote_WhenNotVoted_CreatesVoteAndIncreases() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(false);
        when(postRepository.findById(10L)).thenReturn(Optional.of(mockPostEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));
        when(postVoteRepository.save(any())).thenReturn(mockVote);

        PostVoteEntity result = postVoteService.createPostVote(mockPostVoteInputDto);

        assertNotNull(result);
        verify(postRepository).save(mockPostEntity);
        verify(postVoteRepository).save(any(PostVoteEntity.class));
    }

    @Test
    void testIsPostVoted_ReturnsTrue() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(true);
        assertTrue(postVoteService.isPostVoted(1L, 10L));
        verify(postVoteRepository).isPostVoted(1L, 10L);
    }

    @Test
    void testIsPostVoted_ReturnsFalse() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(false);
        assertFalse(postVoteService.isPostVoted(1L, 10L));
        verify(postVoteRepository).isPostVoted(1L, 10L);
    }

    @Test
    void testCreatePostVote_PostNotFound_ThrowsException() {
        when(postRepository.findById(10L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                postVoteService.createPostVote(mockPostVoteInputDto));

        assertEquals("Post not found with ID: 10", exception.getMessage());
    }

    @Test
    void testCreatePostVote_UserNotFound_ThrowsException() {
        when(postRepository.findById(10L)).thenReturn(Optional.of(mockPostEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                postVoteService.createPostVote(mockPostVoteInputDto));

        assertEquals("User not found with ID: 1", exception.getMessage());
    }

}
