package com.backend.servicetest;

import com.backend.persistence.entity.UserEntity;
import com.backend.repository.UserRepository;
import com.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = UserEntity.builder()
                .id(1L)
                .name("Test User")
                .username("testuser")
                .email("test@example.com")
                .password("securepassword")
                .role("USER")
                .build();
    }

    @Test
    void testFindUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        UserEntity result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void testFindUserById_UserDoesNotExist() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        UserEntity result = userService.findUserById(999L);

        assertNull(result);
        verify(userRepository).findById(999L);
    }
}
