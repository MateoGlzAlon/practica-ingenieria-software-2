package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.backend.controller.impl.TagControllerImpl;
import com.backend.controller.impl.TipControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.TagService;
import com.backend.service.TipService;

public class TagControllerImplTest {
    @Mock
    private TagService tagService;

    @InjectMocks    
    private TagControllerImpl tagController;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private TagOutputDTO mockTagOutputDTO;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
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


        mockUserOutputDto = UserOutputDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role("USER")
                .about("about user")
                .build();

    }

    @Test
    public void testFindTagById_ReturnsTag() {
        when(tagService.findTagById(1L)).thenReturn(mockTagEntity);

        TagEntity result = tagController.findTagById(1L);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(tagService, times(1)).findTagById(1L);
    }

    @Test
    public void testFindTagById_ReturnsNullIfNotFound() {
        when(tagService.findTagById(99L)).thenReturn(null);

        TagEntity result = tagController.findTagById(99L);

        assertNull(result);
        verify(tagService, times(1)).findTagById(99L);
    }

    @Test
    public void testFindTags_ReturnsTagOutputDTO() {
        when(tagService.findTags()).thenReturn(mockTagOutputDTO);

        TagOutputDTO result = tagController.findTags();

        assertNotNull(result);
        assertEquals(1, result.getTags().size());
        assertEquals("mockTag", result.getTags().get(0));
        verify(tagService, times(1)).findTags();
    }

    @Test
    public void testFindTags_DefaultPagination() {
        // Simula que no se pasan parámetros explícitos (usa los valores por defecto)
        when(tagService.findTags()).thenReturn(mockTagOutputDTO);

        TagOutputDTO result = tagController.findTags();

        assertNotNull(result);
        assertEquals(1, result.getTags().size());
        verify(tagService, times(1)).findTags();
    }

}
