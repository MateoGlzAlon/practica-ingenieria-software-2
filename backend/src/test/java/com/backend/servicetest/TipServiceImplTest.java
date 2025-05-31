package com.backend.servicetest;

import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.TipInputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.TipRepository;
import com.backend.repository.UserRepository;
import com.backend.service.impl.TipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class TipServiceImplTest {

    @Mock
    private TipRepository tipRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TipServiceImpl tipService;

    private UserEntity sender;
    private UserEntity receiver;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sender = UserEntity.builder()
                .id(1L)
                .username("sender")
                .wallet(500.0)
                .build();

        receiver = UserEntity.builder()
                .id(2L)
                .username("receiver")
                .wallet(100.0)
                .build();
    }

    @Test
    void testFindTipById_ReturnsTip() {
        TipEntity tip = new TipEntity();
        tip.setId(1L);
        when(tipRepository.findById(1L)).thenReturn(Optional.of(tip));

        TipEntity result = tipService.findTipById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindTipById_NotFound_ReturnsNull() {
        when(tipRepository.findById(99L)).thenReturn(Optional.empty());

        TipEntity result = tipService.findTipById(99L);
        assertNull(result);
    }

    @Test
    void testSendTip_Success() {
        TipInputDTO dto = TipInputDTO.builder()
                .senderId(1L)
                .receiverId(2L)
                .amount(100)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));

        tipService.sendTip(dto);

        assertEquals(400, sender.getWallet());
        assertEquals(200, receiver.getWallet());
        verify(userRepository).save(sender);
        verify(userRepository).save(receiver);
        verify(tipRepository).save(any(TipEntity.class));
    }

    @Test
    void testSendTip_InsufficientBalance_ThrowsException() {
        TipInputDTO dto = TipInputDTO.builder()
                .senderId(1L)
                .receiverId(2L)
                .amount(1000)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tipService.sendTip(dto));
        assertEquals("BALANCE ERROR: Sender does not have enough balance", exception.getMessage());
    }

    @Test
    void testSendTip_SenderNotFound_ThrowsException() {
        TipInputDTO dto = TipInputDTO.builder()
                .senderId(1L)
                .receiverId(2L)
                .amount(50)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tipService.sendTip(dto));
        assertEquals("USER HAS NOT BEEN FOUND: Sender not found", exception.getMessage());
    }

    @Test
    void testSendTip_ReceiverNotFound_ThrowsException() {
        TipInputDTO dto = TipInputDTO.builder()
                .senderId(1L)
                .receiverId(2L)
                .amount(50)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tipService.sendTip(dto));
        assertEquals("USER HAS NOT BEEN FOUND: Receiver not found", exception.getMessage());
    }
}