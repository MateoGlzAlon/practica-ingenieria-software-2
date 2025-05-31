package com.backend.servicetest;

import com.backend.exception.UserNotFoundException;
import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.CommentVoteInputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.CommentVoteRepository;
import com.backend.repository.UserRepository;
import com.backend.service.impl.CommentVoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class CommentVoteServiceImplTest {

    @Mock
    private CommentVoteRepository commentVoteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentVoteServiceImpl commentVoteService;

    private CommentVoteEntity mockVote;
    private CommentEntity mockComment;
    private UserEntity mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = UserEntity.builder().id(1L).username("user1").build();
        mockComment = CommentEntity.builder().id(1L).votes(0).build();
        mockVote = CommentVoteEntity.builder().id(1L).user(mockUser).comment(mockComment).build();
    }

    @Test
    void testFindCommentById_ReturnsVote() {
        when(commentVoteRepository.findById(1L)).thenReturn(Optional.of(mockVote));

        CommentVoteEntity result = commentVoteService.findCommentVoteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(commentVoteRepository).findById(1L);
    }

    @Test
    void testFindCommentById_NotFound_ReturnsNull() {
        when(commentVoteRepository.findById(99L)).thenReturn(Optional.empty());

        CommentVoteEntity result = commentVoteService.findCommentVoteById(99L);

        assertNull(result);
        verify(commentVoteRepository).findById(99L);
    }

    @Test
    void testCreateCommentVote_FirstTime_Success() {
        CommentVoteInputDTO input = CommentVoteInputDTO.builder().userId(1L).commentId(1L).build();

        when(commentVoteRepository.isCommentVoted(1L, 1L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));
        when(commentVoteRepository.save(any(CommentVoteEntity.class))).thenAnswer(i -> i.getArgument(0));

        CommentVoteEntity result = commentVoteService.createCommentVote(input);

        assertNotNull(result);
        assertEquals(mockUser, result.getUser());
        assertEquals(mockComment, result.getComment());
        verify(commentVoteRepository).save(any(CommentVoteEntity.class));
        verify(commentRepository).save(mockComment);
    }

    @Test
    void testCreateCommentVote_AlreadyVoted_RemovesVote() {
        CommentVoteInputDTO input = CommentVoteInputDTO.builder().userId(1L).commentId(1L).build();

        when(commentVoteRepository.isCommentVoted(1L, 1L)).thenReturn(true);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        CommentVoteEntity result = commentVoteService.createCommentVote(input);

        assertNull(result); // voto eliminado
        verify(commentVoteRepository).deleteByUserIDProjectId(1L, 1L);
        verify(commentRepository).save(mockComment);
    }


    @Test
    void testCreateCommentVote_CommentNotFound_ThrowsException() {
        CommentVoteInputDTO input = CommentVoteInputDTO.builder().userId(1L).commentId(99L).build();

        when(commentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            commentVoteService.createCommentVote(input);
        });
    }

    @Test
    void testCreateCommentVote_UserNotFound_ThrowsException() {
        CommentVoteInputDTO input = CommentVoteInputDTO.builder().userId(99L).commentId(1L).build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            commentVoteService.createCommentVote(input);
        });
    }

    @Test
    void testCreateCommentVote_NewVote_SavesVote() {
        CommentVoteInputDTO input = CommentVoteInputDTO.builder().userId(1L).commentId(1L).build();

        when(commentVoteRepository.isCommentVoted(1L, 1L)).thenReturn(false);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        CommentVoteEntity savedVote = CommentVoteEntity.builder()
                .user(mockUser)
                .comment(mockComment)
                .build();

        when(commentVoteRepository.save(any(CommentVoteEntity.class))).thenReturn(savedVote);

        CommentVoteEntity result = commentVoteService.createCommentVote(input);

        assertNotNull(result);
        assertEquals(mockUser, result.getUser());
        assertEquals(mockComment, result.getComment());
        assertEquals(1, mockComment.getVotes()); // 3 + 1
        verify(commentRepository).save(mockComment);
        verify(commentVoteRepository).save(any(CommentVoteEntity.class));
    }


    @Test
    void testIsCommentVoted_ReturnsTrue() {
        when(commentVoteRepository.isCommentVoted(1L, 1L)).thenReturn(true);
        boolean result = commentVoteService.isCommentVoted(1L, 1L);
        assertTrue(result);
    }

    @Test
    void testIsCommentVoted_NullUserId_ReturnsFalse() {
        boolean result = commentVoteService.isCommentVoted(null, 1L);
        assertFalse(result);
    }
}