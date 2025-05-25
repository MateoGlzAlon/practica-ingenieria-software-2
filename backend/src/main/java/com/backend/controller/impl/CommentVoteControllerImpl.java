package com.backend.controller.impl;

import com.backend.controller.CommentVoteController;
import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.inputDTO.CommentVoteInputDTO;
import com.backend.repository.CommentVoteRepository;
import com.backend.service.CommentVoteService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commentvotes")
@AllArgsConstructor
public class CommentVoteControllerImpl implements CommentVoteController {

    private final CommentVoteService commentVoteService;

    @GetMapping("/{id}")
    public CommentVoteEntity findCommentVoteById(@PathVariable Long id){
        return commentVoteService.findCommentVoteById(id);
    }

    @Override
    @PostMapping
    public CommentVoteEntity createCommentVote(@RequestBody CommentVoteInputDTO commentVote) {
        return commentVoteService.createCommentVote(commentVote);
    }

    @Override
    @GetMapping("/check")
    public boolean isCommentVoted(@RequestParam Long userId, @RequestParam Long commentId){
        return commentVoteService.isCommentVoted(userId,commentId);
    }
}
