package com.backend.servicetest;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.UserRepository;
import com.backend.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentEntity mockComment;
    private UserEntity mockUser;
    private PostEntity mockPost;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockUser = UserEntity.builder()
                .id(1L)
                .username("user1")
                .build();

        mockPost = PostEntity.builder()
                .id(1L)
                .title("post1")
                .build();

        mockComment = CommentEntity.builder()
                .id(1L)
                .content("Nice post!")
                .votes(3)
                .user(mockUser)
                .post(mockPost)
                .createdAt(new Date())
                .build();
    }

    @Test
    void testFindCommentById_ReturnsComment() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));

        CommentEntity result = commentService.findCommentById(1L);

        assertNotNull(result);
        assertEquals("Nice post!", result.getContent());
        verify(commentRepository).findById(1L);
    }

    @Test
    void testFindCommentById_NotFound_ReturnsNull() {
        when(commentRepository.findById(999L)).thenReturn(Optional.empty());

        CommentEntity result = commentService.findCommentById(999L);

        assertNull(result);
        verify(commentRepository).findById(999L);
    }

    @Test
    void testFindCommentsOfAPost_ReturnsListOfDTOs() {
        when(commentRepository.findByPostId(1L)).thenReturn(List.of(mockComment));

        List<CommentOutputDTO> result = commentService.findCommentsOfAPost(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Nice post!", result.get(0).getContent());
    }

    @Test
    void testCreateComment_Success() {
        CommentInputDTO input = CommentInputDTO.builder()
                .postId(1L)
                .userId(1L)
                .content("This is a new comment")
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(commentRepository.save(any(CommentEntity.class))).thenAnswer(invocation -> {
            CommentEntity comment = invocation.getArgument(0);
            comment.setId(42L);
            return comment;
        });

        CommentEntity created = commentService.createComment(input);

        assertNotNull(created);
        assertEquals("This is a new comment", created.getContent());
        assertEquals(0, created.getVotes());
        assertEquals(42L, created.getId());
    }
}
