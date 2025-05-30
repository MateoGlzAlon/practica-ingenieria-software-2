package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.backend.controller.impl.UserControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.ProfileDTO;

import com.backend.service.UserService;

public class UserControllerImplTest {

    @Mock
    private UserService userService;

    @InjectMocks    
    private UserControllerImpl userController;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private UserInputDTO mockUserInputDto;
    private ProfileDTO mockProfileDto;
    private UserOutputDTO mockUserOutputDto;

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
    }

    @Test
    public void testFindUserById_ReturnsUserEntity() {
        when(userService.findUserById(1L)).thenReturn(mockUserEntity);

        UserEntity result = userController.findUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    public void testFindInputUserById_ReturnsUserInputDTO() {
        when(userService.findUserInputByID(1L)).thenReturn(mockUserInputDto);

        UserInputDTO result = userController.findInputUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).findUserInputByID(1L);
    }

    @Test
    public void testGetProfileByUserId_ReturnsProfileDTO() {
        when(userService.getProfileByUserId(1L)).thenReturn(mockProfileDto);

        ProfileDTO result = userController.getProfileByUserId(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUser().getUsername());
        verify(userService, times(1)).getProfileByUserId(1L);
    }

}
