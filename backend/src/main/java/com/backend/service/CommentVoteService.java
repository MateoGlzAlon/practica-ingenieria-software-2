package com.backend.service;

import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.inputDTO.CommentVoteInputDTO;

public interface CommentVoteService {
    CommentVoteEntity findCommentVoteById(Long id);

    CommentVoteEntity createCommentVote(CommentVoteInputDTO commentVote);

    boolean isCommentVoted(Long userId, Long commentId);
}
