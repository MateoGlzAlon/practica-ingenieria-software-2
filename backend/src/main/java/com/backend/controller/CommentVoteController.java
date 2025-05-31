package com.backend.controller;

import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.inputDTO.CommentVoteInputDTO;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/commentvotes")
public interface CommentVoteController {
    CommentVoteEntity findCommentVoteById(Long id);

    @GetMapping("/check")
    boolean isCommentVoted(@RequestParam(required = false) Long userId,@RequestParam Long commentId);

    @PostMapping
    CommentVoteEntity createCommentVote(CommentVoteInputDTO commentVote);
}
