package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.TipInputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.TipRepository;
import com.backend.repository.UserRepository;
import com.backend.service.TipService;
import lombok.AllArgsConstructor;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipRepository tipRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public TipEntity findTipById(Long id) {
        return tipRepository.findById(id).orElse(null);
    }

    @Override
    public void sendTip(TipInputDTO dto) {
        UserEntity sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        UserEntity receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getWallet() < dto.getAmount()) {
            throw new RuntimeException("Sender does not have enough balance");
        }

        sender.setWallet(sender.getWallet() - dto.getAmount());
        receiver.setWallet(receiver.getWallet() + dto.getAmount());

        userRepository.save(sender);
        userRepository.save(receiver);

        TipEntity tip = new TipEntity();
        tip.setSender(sender);
        tip.setReceiver(receiver);
        tip.setAmount(dto.getAmount());
        tip.setCreatedAt(new Date());

        tipRepository.save(tip);
    }

}