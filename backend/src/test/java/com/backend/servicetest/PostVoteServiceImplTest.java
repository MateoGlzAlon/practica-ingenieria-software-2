package com.backend.servicetest;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.PostVoteInputDTO;
import com.backend.repository.*;
import com.backend.service.impl.PostVoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostVoteServiceImplTest {

    @Mock private PostVoteRepository postVoteRepository;
    @Mock private UserRepository userRepository;
    @Mock private PostRepository postRepository;
    @Mock private CommentRepository commentRepository;

    @InjectMocks private PostVoteServiceImpl postVoteService;

    private PostVoteEntity mockVote;
    private PostVoteInputDTO inputDTO;
    private UserEntity mockUser;
    private PostEntity mockPost;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockVote = PostVoteEntity.builder()
                .id(1L)
                .post(null)
                .user(null)
                .build();

        inputDTO = new PostVoteInputDTO();
        inputDTO.setUserId(1L);
        inputDTO.setPostId(10L);

        mockUser = new UserEntity();
        mockPost = new PostEntity();
    }

    @Test
    public void testFindPostById_ReturnsPostVote() {
        when(postVoteRepository.findById(1L)).thenReturn(Optional.of(mockVote));
        PostVoteEntity result = postVoteService.findPostVoteById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(postVoteRepository).findById(1L);
    }

    @Test
    public void testFindPostById_ReturnsNullWhenNotFound() {
        when(postVoteRepository.findById(2L)).thenReturn(Optional.empty());
        PostVoteEntity result = postVoteService.findPostVoteById(2L);
        assertNull(result);
        verify(postVoteRepository).findById(2L);
    }

    @Test
    public void testCreatePostVote_WhenPostIsNull_ReturnsNull() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(false);
        when(postRepository.findById(10L)).thenReturn(Optional.empty());

        PostVoteEntity result = postVoteService.createPostVote(inputDTO);
        assertNull(result);
        verify(postRepository).findById(10L);
    }

    @Test
    public void testCreatePostVote_WhenAlreadyVoted_DeletesVoteAndDecreases() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(true);
        when(postRepository.findById(10L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        PostVoteEntity result = postVoteService.createPostVote(inputDTO);

        assertNull(result);
        verify(postVoteRepository).deleteByUserIDProjectId(1L, 10L);
        verify(postRepository).save(mockPost);
    }

    @Test
    public void testCreatePostVote_WhenNotVoted_CreatesVoteAndIncreases() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(false);
        when(postRepository.findById(10L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postVoteRepository.save(any())).thenReturn(mockVote);

        PostVoteEntity result = postVoteService.createPostVote(inputDTO);

        assertNotNull(result);
        verify(postRepository).save(mockPost);
        verify(postVoteRepository).save(any(PostVoteEntity.class));
    }

    @Test
    public void testIsPostVoted_ReturnsTrue() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(true);
        assertTrue(postVoteService.isPostVoted(1L, 10L));
        verify(postVoteRepository).isPostVoted(1L, 10L);
    }

    @Test
    public void testIsPostVoted_ReturnsFalse() {
        when(postVoteRepository.isPostVoted(1L, 10L)).thenReturn(false);
        assertFalse(postVoteService.isPostVoted(1L, 10L));
        verify(postVoteRepository).isPostVoted(1L, 10L);
    }
}
