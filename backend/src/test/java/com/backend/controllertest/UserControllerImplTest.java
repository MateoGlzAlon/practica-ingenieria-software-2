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

import com.backend.persistence.inputDTO.UserLinksInputDTO;
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

 class UserControllerImplTest {

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
     void setup() {
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
    void testFindUserById_ReturnsUserEntity() {
        when(userService.findUserById(1L)).thenReturn(mockUserEntity);

        UserEntity result = userController.findUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    void testFindInputUserById_ReturnsUserInputDTO() {
        when(userService.findUserInputByID(1L)).thenReturn(mockUserInputDto);

        UserInputDTO result = userController.findInputUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).findUserInputByID(1L);
    }

    @Test
    void testGetProfileByUserId_ReturnsProfileDTO() {
        when(userService.getProfileByUserId(1L)).thenReturn(mockProfileDto);

        ProfileDTO result = userController.getProfileByUserId(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUser().getUsername());
        verify(userService, times(1)).getProfileByUserId(1L);
    }

    @Test
    void testChangeUserLinks_ReturnsUpdatedUser() {
        UserLinksInputDTO linksDTO = UserLinksInputDTO.builder()
                .userId(1L)
                .github_link("https://github.com/test")
                .twitter_link("https://twitter.com/test")
                .website_link("https://test.com")
                .build();

        when(userService.changeUserLinks(linksDTO)).thenReturn(mockUserEntity);

        UserEntity result = userController.changeUserLinks(linksDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).changeUserLinks(linksDTO);
    }

    @Test
    void testGetUserIdByEmail_ReturnsId() {
        when(userService.getUserIdByEmail("test@example.com")).thenReturn(1L);

        Long result = userController.getUserIdByEmail("test@example.com");

        assertEquals(1L, result);
        verify(userService, times(1)).getUserIdByEmail("test@example.com");
    }

    @Test
    void testGetDummy_ReturnsDummyString() {
        String result = userController.getDummy();

        assertEquals("Dummy", result);
    }


}
