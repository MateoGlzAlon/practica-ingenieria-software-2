package com.backend.servicetest;

import com.backend.persistence.entity.PostImageEntity;
import com.backend.repository.PostImageRepository;
import com.backend.service.impl.PostImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostImageServiceImplTest {

    @Mock
    private PostImageRepository postImageRepository;

    @InjectMocks
    private PostImageServiceImpl postImageService;

    private PostImageEntity mockImage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockImage = PostImageEntity.builder()
                .id(1L)
                .imageUrl("https://placehold.co/600x400?text=TestImage")
                .post(null) // puedes mockear un PostEntity si deseas
                .build();
    }

    @Test
    void testFindPostImageById_ReturnsImage() {
        when(postImageRepository.findById(1L)).thenReturn(Optional.of(mockImage));

        PostImageEntity result = postImageService.findPostImageById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("https://placehold.co/600x400?text=TestImage", result.getImageUrl());
        verify(postImageRepository).findById(1L);
    }

    @Test
    void testFindPostImageById_ReturnsNullIfNotFound() {
        when(postImageRepository.findById(99L)).thenReturn(Optional.empty());

        PostImageEntity result = postImageService.findPostImageById(99L);

        assertNull(result);
        verify(postImageRepository).findById(99L);
    }
}
