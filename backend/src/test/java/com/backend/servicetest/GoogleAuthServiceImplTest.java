package com.backend.servicetest;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.repository.UserRepository;
import com.backend.service.impl.GoogleAuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoogleAuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GoogleAuthServiceImpl googleAuthService;

    private GoogleLoginDTO loginDTO;
    private UserEntity existingUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        loginDTO = new GoogleLoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setUsername("test");
        loginDTO.setAvatarUrl("https://avatar.com/test");

        existingUser = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .username("test")
                .avatarUrl("https://avatar.com/test")
                .createdAt(new Date())
                .build();
    }

    @Test
    public void testAuthenticateWithGoogle_UserExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        ResponseEntity<?> response = googleAuthService.authenticateWithGoogle(loginDTO);

        assertEquals(200, response.getStatusCodeValue());
        UserEntity result = (UserEntity) response.getBody();
        assertNotNull(result);
        assertEquals("test", result.getUsername());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testAuthenticateWithGoogle_NewUserCreated() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> {
            UserEntity user = i.getArgument(0);
            user.setId(99L); // simulate saved ID
            return user;
        });

        ResponseEntity<?> response = googleAuthService.authenticateWithGoogle(loginDTO);

        assertEquals(200, response.getStatusCodeValue());
        UserEntity createdUser = (UserEntity) response.getBody();
        assertNotNull(createdUser);
        assertEquals("test", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("https://avatar.com/test", createdUser.getAvatarUrl());
        verify(userRepository).save(any(UserEntity.class));
    }
}
