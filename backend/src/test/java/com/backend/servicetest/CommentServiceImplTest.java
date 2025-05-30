package com.backend.servicetest;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.CommentAcceptDTO;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.persistence.outputdto.UserCommentDTO;
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

        mockUser = UserEntity.builder().id(1L).username("user1").avatarUrl("avatar.png").build();
        mockPost = PostEntity.builder().id(1L).title("post1").comments(new ArrayList<>()).user(mockUser).build();
        mockComment = CommentEntity.builder()
                .id(1L)
                .content("Nice post!")
                .votes(3)
                .user(mockUser)
                .post(mockPost)
                .createdAt(new Date())
                .accepted(false)
                .build();

        mockPost.getComments().add(mockComment);
    }

    @Test
    void testFindCommentById_ReturnsComment() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));
        CommentEntity result = commentService.findCommentById(1L);
        assertNotNull(result);
        assertEquals("Nice post!", result.getContent());
    }

    @Test
    void testFindCommentById_NotFound_ReturnsNull() {
        when(commentRepository.findById(999L)).thenReturn(Optional.empty());
        CommentEntity result = commentService.findCommentById(999L);
        assertNull(result);
    }

    @Test
    void testFindCommentsOfAPost_ByVotes() {
        when(commentRepository.findByPostIdOrderByVotesDesc(1L)).thenReturn(List.of(mockComment));
        List<CommentOutputDTO> result = commentService.findCommentsOfAPost(1L, "votes");
        assertEquals(1, result.size());
        assertEquals("Nice post!", result.get(0).getContent());
    }

    @Test
    void testFindCommentsOfAPost_ByOldest() {
        when(commentRepository.findByPostIdOrderByCreatedAtAsc(1L)).thenReturn(List.of(mockComment));
        List<CommentOutputDTO> result = commentService.findCommentsOfAPost(1L, "oldest");
        assertEquals(1, result.size());
    }

    @Test
    void testFindCommentsOfAPost_ByNewestDefault() {
        when(commentRepository.findByPostIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(mockComment));
        List<CommentOutputDTO> result = commentService.findCommentsOfAPost(1L, "random");
        assertEquals(1, result.size());
    }

    @Test
    void testCreateComment_Success() {
        CommentInputDTO input = CommentInputDTO.builder().postId(1L).userId(1L).content("New Comment").build();
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(commentRepository.save(any(CommentEntity.class))).thenAnswer(i -> {
            CommentEntity c = i.getArgument(0);
            c.setId(2L);
            return c;
        });
        CommentEntity created = commentService.createComment(input);
        assertEquals("New Comment", created.getContent());
        assertEquals(0, created.getVotes());
    }

    @Test
    void testAcceptComment_Success() {
        CommentAcceptDTO acceptDTO = new CommentAcceptDTO(1L, 1L, 1L);
        when(postRepository.findPostsByUserId(1L)).thenReturn(List.of(mockPost));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));
        when(commentRepository.save(any(CommentEntity.class))).thenAnswer(i -> i.getArgument(0));

        CommentEntity result = commentService.acceptComment(acceptDTO);
        assertTrue(result.isAccepted());
    }

    @Test
    void testAcceptComment_InvalidUser_ReturnsNull() {
        CommentAcceptDTO acceptDTO = new CommentAcceptDTO(2L, 1L, 1L);
        when(postRepository.findPostsByUserId(2L)).thenReturn(Collections.emptyList());
        CommentEntity result = commentService.acceptComment(acceptDTO);
        assertNull(result);
    }

    @Test
    void testAcceptComment_CommentNotFound_ReturnsNull() {
        CommentAcceptDTO acceptDTO = new CommentAcceptDTO(1L, 1L, 99L); // commentId inválido
        when(postRepository.findPostsByUserId(1L)).thenReturn(List.of(mockPost));
        when(commentRepository.findById(99L)).thenReturn(Optional.empty());

        CommentEntity result = commentService.acceptComment(acceptDTO);

        assertNull(result);
    }


    @Test
    void testGetCommentsOfAUser_Success() {
        when(commentRepository.findByUserId(1L)).thenReturn(new ArrayList<>(List.of(mockComment)));
        List<UserCommentDTO> result = commentService.getCommentsOfAUser(1L);
        assertEquals(1, result.size());
        assertEquals("Nice post!", result.get(0).getContent());
    }

    @Test
    void testGetCommentsByPostIdOrderByVotes() {
        when(commentRepository.findByPostIdOrderByVotesDesc(1L)).thenReturn(List.of(mockComment));
        List<CommentEntity> result = commentService.getCommentsByPostIdOrderByVotes(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetCommentsByPostIdOrderByNewest() {
        when(commentRepository.findByPostIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(mockComment));
        List<CommentEntity> result = commentService.getCommentsByPostIdOrderByNewest(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetCommentsByPostIdOrderByOldest() {
        when(commentRepository.findByPostIdOrderByCreatedAtAsc(1L)).thenReturn(List.of(mockComment));
        List<CommentEntity> result = commentService.getCommentsByPostIdOrderByOldest(1L);
        assertEquals(1, result.size());
    }
}
