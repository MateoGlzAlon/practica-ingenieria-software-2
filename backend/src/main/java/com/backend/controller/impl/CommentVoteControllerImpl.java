package com.backend.controller.impl;

import com.backend.controller.CommentVoteController;
import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.repository.CommentVoteRepository;
import com.backend.service.CommentVoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commentvotes")
@AllArgsConstructor
public class CommentVoteControllerImpl implements CommentVoteController {

    private final CommentVoteService commentVoteService;

    @GetMapping("/{id}")
    public CommentVoteEntity findCommentVoteById(@PathVariable Long id){
        return commentVoteService.findCommentVoteById(id);
    }
}
