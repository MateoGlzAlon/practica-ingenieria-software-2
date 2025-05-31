package com.backend.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import com.backend.persistence.inputDTO.TipInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.controller.impl.TipControllerImpl;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.service.TipService;
import org.springframework.http.ResponseEntity;

 class TipControllerImplTest {
    @Mock
    private TipService tipService;

    @InjectMocks    
    private TipControllerImpl tipController;

    private UserEntity mockUserEntity;
    private TagEntity mockTagEntity;
    private PostEntity mockPostEntity;
    private PostImageEntity mockPostImageEntity;
    private TipEntity mockTipEntity;

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

        mockTipEntity = TipEntity.builder()
                .id(1L)
                .amount(100)
                .createdAt(new Date())
                .build();
    }

    @Test
    void testFindTipById_TipExists() {
        when(tipService.findTipById(1L)).thenReturn(mockTipEntity);

        TipEntity result = tipController.findTipById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100, result.getAmount());
        verify(tipService, times(1)).findTipById(1L);
    }

    @Test
    void testFindTipById_TipDoesNotExist() {
        when(tipService.findTipById(99L)).thenReturn(null);

        TipEntity result = tipController.findTipById(99L);

        assertNull(result);
        verify(tipService, times(1)).findTipById(99L);
    }

    @Test
    void testSendTip_Success() {
        TipInputDTO dto = TipInputDTO.builder()
                .senderId(1L)
                .receiverId(2L)
                .amount(100)
                .build();

        ResponseEntity<String> response = tipController.sendTip(dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Tip sent successfully", response.getBody());
        verify(tipService, times(1)).sendTip(dto);
    }

    @Test
    void testSendTip_ThrowsRuntimeException_ReturnsBadRequest() {
        TipInputDTO dto = TipInputDTO.builder()
                .senderId(1L)
                .receiverId(2L)
                .amount(100)
                .build();

        doThrow(new RuntimeException("Saldo insuficiente")).when(tipService).sendTip(dto);

        ResponseEntity<String> response = tipController.sendTip(dto);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Saldo insuficiente", response.getBody());
        verify(tipService).sendTip(dto);
    }
}
