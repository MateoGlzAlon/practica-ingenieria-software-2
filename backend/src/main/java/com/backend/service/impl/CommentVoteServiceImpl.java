package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.repository.CommentVoteRepository;
import com.backend.service.CommentVoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentVoteServiceImpl implements CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;

    @Override
    public CommentVoteEntity findCommentById(Long id) {
        return commentVoteRepository.findById(id).orElse(null);
    }
}
