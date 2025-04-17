package com.backend.controller;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/comments")
public interface CommentController {

    @GetMapping("/{id}")
    CommentEntity findCommentById(@PathVariable Long id);

    @GetMapping("/post/{id}")
    List<CommentInputDTO> findCommentsOfAPost(@PathVariable Long id);

}
