package com.backend.controller;

import com.backend.persistence.entity.CommentVoteEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/commentvotes")
public interface CommentVoteController {
    CommentVoteEntity findCommentById(Long id);
}
