package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
import org.springframework.http.ResponseEntity;

import com.backend.controller.impl.GoogleAuthControllerImpl;
import com.backend.controller.impl.PostControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.CommunityStatsDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.GoogleAuthService;
import com.backend.service.PostService;

public class GoogleAuthControllerImplTest {
    @Mock
    private GoogleAuthService googleAuthService;

    @InjectMocks
    private GoogleAuthControllerImpl googleAuthController;

    private PostInputDTO mockPostInput;
    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private TagOutputDTO mockTagOutputDTO;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private TipEntity mockTipEntity;
    private UserInputDTO mockUserInputDto;
    private ProfileDTO mockProfileDto;
    private UserOutputDTO mockUserOutputDto;
    private CommunityStatsDTO mockStats;
    private PostImageEntity mockImage;
    private PostEntity mockPost;
    private PostOutputDTO mockPostOutput;
    private PostDetailsDTO mockPostDetails;
    private FeedPostDTO mockFeedPost;
    private GoogleLoginDTO mockLoginDTO;


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

        mockTagOutputDTO = TagOutputDTO.builder()
                .tags(List.of("mockTag"))
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

        PostEntity post = PostEntity.builder()
                .id(1L)
                .title("Post Title")
                .build();

        mockTipEntity = TipEntity.builder()
                .id(1L)
                .amount(100)
                .post(post)
                .createdAt(new Date())
                .build();

        
        mockUserInputDto = UserInputDTO.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .about("about user")
                .build();

        mockUserOutputDto = UserOutputDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .about("about user")
                .build();
        mockProfileDto = ProfileDTO.builder()
                .user(mockUserOutputDto)
                .build();

        mockStats = CommunityStatsDTO.builder()
                .users(50L)
                .questions(200L)
                .answers (300L)
                .build();

        mockImage = PostImageEntity.builder()
                .id(1L)
                .imageUrl("https://placehold.co/600x400?text=PostImage")
                .build();

        mockPostInput = PostInputDTO.builder()
                .title("Mock Post")
                .content("Content")
                .tagId(1L)
                .userId(1L)
                .build();

        mockPostOutput = PostOutputDTO.builder()
                .id(1L)
                .title("Mock Post")
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

        mockLoginDTO = GoogleLoginDTO.builder()
                .email("user@example.com")
                .username("testuser")
                .avatarUrl("https://example.com/avatar.jpg")
                .build();
    }

    @Test
    public void testLoginWithGoogle_ReturnsResponse() {
        ResponseEntity<?> mockResponse = ResponseEntity.ok("Login success");

        when(googleAuthService.authenticateWithGoogle(any(GoogleLoginDTO.class))).thenReturn((ResponseEntity) mockResponse);

        ResponseEntity<?> result = googleAuthController.loginWithGoogle(mockLoginDTO);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Login success", result.getBody());

        verify(googleAuthService, times(1)).authenticateWithGoogle(mockLoginDTO);
    }
}
