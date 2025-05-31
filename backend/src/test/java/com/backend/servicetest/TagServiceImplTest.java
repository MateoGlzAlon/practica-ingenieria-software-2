package com.backend.servicetest;

import com.backend.persistence.entity.*;
import com.backend.persistence.outputdto.TagCreatePostDTO;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.repository.*;
import com.backend.service.impl.TagServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

 class TagServiceImplTest {


    @Mock
    private TagRepository tagRepository;

    @InjectMocks private TagServiceImpl tagService;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private TagEntity tag1;
    private TagEntity tag2;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);


        mockUserEntity = UserEntity.builder()
                .id(1L)
                .username("testuser")
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

        tag1 = TagEntity.builder()
                .id(1L)
                .name("Java")
                .build();

        tag2 = TagEntity.builder()
                .id(2L)
                .name("Python")
                .build();
        
    }

    @Test
    void testFindTagById_ReturnsTag() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag1));

        TagEntity result = tagService.findTagById(1L);

        assertNotNull(result);
        assertEquals("Java", result.getName());
    }

    @Test
    void testFindTagById_ReturnsNull() {
        when(tagRepository.findById(999L)).thenReturn(Optional.empty());

        TagEntity result = tagService.findTagById(999L);

        assertNull(result);
    }

    @Test
    void testFindTags_ReturnsDTO() {
        List<TagEntity> tags = Arrays.asList(tag1, tag2);
        Sort sort = Sort.by(Sort.Direction.DESC, "name");

        when(tagRepository.findAll(sort)).thenReturn(tags);

        TagOutputDTO result = tagService.findTags();

        assertNotNull(result);
        assertEquals(2, result.getTags().size());
        assertTrue(result.getTags().contains("Java"));
        assertTrue(result.getTags().contains("Python"));
    }

    @Test
    void testGetTagsAvailablePost_ReturnsTagCreatePostDTOList() {
        List<TagEntity> tags = Arrays.asList(tag2, tag1); // Python id=2, Java id=1
        when(tagRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(tags);

        List<TagCreatePostDTO> result = tagService.getTagsAvailablePost();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(2L, result.get(0).getId());
        assertEquals("Python", result.get(0).getName());

        assertEquals(1L, result.get(1).getId());
        assertEquals("Java", result.get(1).getName());

        verify(tagRepository).findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
