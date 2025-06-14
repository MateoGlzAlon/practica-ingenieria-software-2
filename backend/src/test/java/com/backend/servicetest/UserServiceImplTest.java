package com.backend.servicetest;

import com.backend.exception.UserNotFoundException;
import com.backend.persistence.entity.*;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.inputDTO.UserLinksInputDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.repository.*;
import com.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private TipRepository tipRepository;
    @Mock
    private CommentRepository commentRepository;


    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private UserInputDTO mockUserInputDto;

    @BeforeEach
     void setupVariables() {
        MockitoAnnotations.openMocks(this);

        mockUserInputDto = UserInputDTO.builder()
                .username("testuser")
                .email("ZMw0m@example.com")
                .password("testpassword")
                .about("testabout")
                .build();

        mockUserEntity = UserEntity.builder()
                .id(1L)
                .name("Red User")
                .username("reduser")
                .email("reduser@email.com")
                .role("USER")
                .about("Sobre mí")
                .avatarUrl("http://avatar.com/img.png")
                .createdAt(new Date())
                .github_link("http://github.com/reduser")
                .twitter_link("http://twitter.com/reduser")
                .website_link("http://reduser.dev")
                .build();

        mockTagEntity = TagEntity.builder()
                .id(1L)
                .name("Tag name")
                .posts(new HashSet<>())
                .build();

        mockPostEntity = PostEntity.builder()
                .id(1L)
                .title("Test Title")
                .votes(5)
                .content("Test Content")
                .user(mockUserEntity)
                .tag(mockTagEntity)
                .comments(new ArrayList<>())
                .images(new ArrayList<>())
                .createdAt(new Date())
                .build();


    }

    @Test
    void testFindUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));

        UserEntity result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals(mockUserEntity.getUsername(), result.getUsername());
        assertEquals(mockUserEntity.getEmail(), result.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void testFindUserById_UserDoesNotExist() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        UserEntity result = userService.findUserById(999L);

        assertNull(result);
        verify(userRepository).findById(999L);
    }

    @Test
    void testFindInputUserById_UserExists() {
        when(userRepository.findInputUserById(1L)).thenReturn(mockUserInputDto);

        UserInputDTO result = userService.findUserInputByID(1L);

        assertNotNull(result);
        assertEquals(mockUserInputDto.getUsername(), result.getUsername());
        assertEquals(mockUserInputDto.getEmail(), result.getEmail());
        verify(userRepository).findInputUserById(1L);
    }

    @Test
    void testGetProfileByUserId_ReturnsProfileDTO() {
        Long userId = 1L;

        // Comentario de prueba relacionado con el post
        CommentEntity comment = CommentEntity.builder()
                .id(100L)
                .content("Comentario de prueba")
                .user(mockUserEntity)
                .post(mockPostEntity)
                .createdAt(new Date())
                .build();

        // Tip enviado
        TipEntity tipSent = TipEntity.builder()
                .id(1L)
                .amount(10)
                .sender(mockUserEntity)
                .receiver(UserEntity.builder().id(2L).build())
                .createdAt(new Date())
                .build();

        // Tip recibido
        TipEntity tipReceived = TipEntity.builder()
                .id(2L)
                .amount(20)
                .sender(UserEntity.builder().username("senderUser").build())
                .receiver(mockUserEntity)
                .createdAt(new Date())
                .build();


        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        when(postRepository.findPostsByUserId(userId)).thenReturn(List.of(mockPostEntity));
        when(tipRepository.findTipsSentByUserId(userId)).thenReturn(List.of(tipSent));
        when(tipRepository.findTipsReceivedByUserId(userId)).thenReturn(List.of(tipReceived));
        when(commentRepository.findByUserId(userId)).thenReturn(List.of(comment));


        List<Object[]> postCounts = Arrays.asList(
                new Object[] { "Jan", 2 },
                new Object[] { "Feb", 1 }
        );

        List<Object[]> commentCounts = Arrays.asList(
                new Object[] { "Jan", 3 },
                new Object[] { "Feb", 2 }
        );


        when(postRepository.findPostCountsByMonth(userId)).thenReturn(postCounts);
        when(commentRepository.findCommentCountsByMonth(userId)).thenReturn(commentCounts);


        // Call service
        ProfileDTO result = userService.getProfileByUserId(userId);

        // Assertions
        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals(mockUserEntity.getUsername(), result.getUser().getUsername());
        assertEquals(1, result.getPosts().size());
        assertEquals(1, result.getUser().getTipsSent().size());
        assertEquals(1, result.getUser().getTipsReceived().size());
        assertEquals(12, result.getActivityData().size());

        assertTrue(result.getActivityData().stream()
                .anyMatch(a -> a.getMonth().equals("Jan") && a.getContributions() == 5));

        // Verifications
        verify(userRepository).findById(userId);
        verify(postRepository).findPostsByUserId(userId);
        verify(tipRepository).findTipsSentByUserId(userId);
        verify(tipRepository).findTipsReceivedByUserId(userId);
        verify(commentRepository).findByUserId(userId);
        verify(postRepository).findPostCountsByMonth(userId);
        verify(commentRepository).findCommentCountsByMonth(userId);
    }


    @Test
    void testGetProfileByUserId_UserDoesNotExist() {
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ProfileDTO result = userService.getProfileByUserId(userId);

        assertNull(result);
        verify(userRepository).findById(userId);
    }

    @Test
    void testChangeUserLinks_Success() {
        UserLinksInputDTO linksDTO = UserLinksInputDTO.builder()
                .userId(1L)
                .github_link("https://github.com/new")
                .website_link("https://mysite.com")
                .twitter_link("https://twitter.com/new")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> i.getArgument(0));

        UserEntity updatedUser = userService.changeUserLinks(linksDTO);

        assertEquals("https://github.com/new", updatedUser.getGithub_link());
        assertEquals("https://mysite.com", updatedUser.getWebsite_link());
        assertEquals("https://twitter.com/new", updatedUser.getTwitter_link());

        verify(userRepository).findById(1L);
        verify(userRepository).save(mockUserEntity);
    }

    @Test
    void testGetUserIdByEmail_Success() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUserEntity));

        Long userId = userService.getUserIdByEmail("user@example.com");

        assertEquals(1L, userId);
        verify(userRepository).findByEmail("user@example.com");
    }

    @Test
    void testChangeUserLinks_UserNotFound_ThrowsException() {
        UserLinksInputDTO input = UserLinksInputDTO.builder()
                .userId(999L)
                .github_link("https://github.com/testuser")
                .website_link("https://example.com")
                .twitter_link("https://twitter.com/testuser")
                .build();

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.changeUserLinks(input);
        });

        assertEquals("USER HAS NOT BEEN FOUND: User not found", thrown.getMessage());
        verify(userRepository).findById(999L);
    }

    @Test
    void testGetUserIdByEmail_UserNotFound_ThrowsException() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserIdByEmail(email);
        });

        assertEquals("USER HAS NOT BEEN FOUND: User not found with email: " + email, thrown.getMessage());
        verify(userRepository).findByEmail(email);
    }



}
