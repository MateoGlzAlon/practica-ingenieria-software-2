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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.backend.controller.impl.GoogleAuthControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.service.GoogleAuthService;

public class GoogleAuthControllerImplTest {
    @Mock
    private GoogleAuthService googleAuthService;

    @InjectMocks
    private GoogleAuthControllerImpl googleAuthController;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private UserOutputDTO mockUserOutputDto;
    private GoogleLoginDTO mockLoginDTO;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

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

        mockUserOutputDto = UserOutputDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .about("about user")
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
