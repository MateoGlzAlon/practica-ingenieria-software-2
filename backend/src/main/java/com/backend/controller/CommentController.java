package com.backend.controller;

import com.backend.persistence.entity.CommentEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/comments")
public interface CommentController {

    @GetMapping("/{id}")
    CommentEntity findCommentById(@PathVariable Long id);

}
