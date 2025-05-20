package com.backend.service;

import com.backend.persistence.entity.CommentVoteEntity;

public interface CommentVoteService {
    CommentVoteEntity findCommentById(Long id);
}
