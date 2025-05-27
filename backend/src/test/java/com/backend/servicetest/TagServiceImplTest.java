package com.backend.servicetest;

import com.backend.persistence.entity.*;
import com.backend.persistence.inputDTO.PostInputDTO;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.specialdto.FeedPostDTO;
import com.backend.persistence.specialdto.PostDetailsDTO;
import com.backend.repository.*;
import com.backend.service.impl.PostServiceImpl;
import com.backend.service.impl.TagServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private TipRepository tipRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostVoteRepository postVoteRepository;
    @Mock
    private CommentVoteRepository commentVoteRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks private TagServiceImpl tagService;

    private PostInputDTO mockPostInput;
    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private TagEntity tag1;
    private TagEntity tag2;
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
    public void testFindTagById_ReturnsTag() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag1));

        TagEntity result = tagService.findTagById(1L);

        assertNotNull(result);
        assertEquals("Java", result.getName());
    }

    @Test
    public void testFindTagById_ReturnsNull() {
        when(tagRepository.findById(999L)).thenReturn(Optional.empty());

        TagEntity result = tagService.findTagById(999L);

        assertNull(result);
    }

    @Test
    public void testFindTags_ReturnsDTO() {
        List<TagEntity> tags = Arrays.asList(tag1, tag2);
        Page<TagEntity> mockPage = new PageImpl<>(tags);
        Pageable pageable = PageRequest.of(0, 10);

        when(tagRepository.findAll(pageable)).thenReturn(mockPage);

        TagOutputDTO result = tagService.findTags(0, 10);

        assertNotNull(result);
        assertEquals(2, result.getTags().size());
        assertTrue(result.getTags().contains("Java"));
        assertTrue(result.getTags().contains("Python"));
    }
}
