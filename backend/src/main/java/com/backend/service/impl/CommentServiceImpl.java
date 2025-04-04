package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.repository.CommentRepository;
import com.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Override
    public CommentEntity findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

}

