package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.backend.persistence.outputdto.TagCreatePostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.controller.impl.TagControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.service.TagService;

 class TagControllerImplTest {
    @Mock
    private TagService tagService;

    @InjectMocks    
    private TagControllerImpl tagController;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private TagOutputDTO mockTagOutputDTO;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;

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

    }

    @Test
    void testFindTagById_ReturnsTag() {
        when(tagService.findTagById(1L)).thenReturn(mockTagEntity);

        TagEntity result = tagController.findTagById(1L);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(tagService, times(1)).findTagById(1L);
    }

    @Test
    void testFindTagById_ReturnsNullIfNotFound() {
        when(tagService.findTagById(99L)).thenReturn(null);

        TagEntity result = tagController.findTagById(99L);

        assertNull(result);
        verify(tagService, times(1)).findTagById(99L);
    }

    @Test
    void testFindTags_ReturnsTagOutputDTO() {
        when(tagService.findTags()).thenReturn(mockTagOutputDTO);

        TagOutputDTO result = tagController.findTags();

        assertNotNull(result);
        assertEquals(1, result.getTags().size());
        assertEquals("mockTag", result.getTags().get(0));
        verify(tagService, times(1)).findTags();
    }

    @Test
    void testFindTags_DefaultPagination() {
        when(tagService.findTags()).thenReturn(mockTagOutputDTO);

        TagOutputDTO result = tagController.findTags();

        assertNotNull(result);
        assertEquals(1, result.getTags().size());
        verify(tagService, times(1)).findTags();
    }

    @Test
    void testGetTagsAvailablePost_ReturnsList() {
        TagCreatePostDTO tag1 = TagCreatePostDTO.builder().id(1L).name("Java").build();
        TagCreatePostDTO tag2 = TagCreatePostDTO.builder().id(2L).name("Spring").build();
        List<TagCreatePostDTO> mockTags = List.of(tag1, tag2);

        when(tagService.getTagsAvailablePost()).thenReturn(mockTags);

        List<TagCreatePostDTO> result = tagController.getTagsAvailablePost();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(tagService, times(1)).getTagsAvailablePost();
    }

    @Test
    void testGetTagsAvailablePost_EmptyList() {
        when(tagService.getTagsAvailablePost()).thenReturn(List.of());

        List<TagCreatePostDTO> result = tagController.getTagsAvailablePost();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(tagService, times(1)).getTagsAvailablePost();
    }



}
