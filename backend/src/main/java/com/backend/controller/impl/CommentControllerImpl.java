package com.backend.controller.impl;

import com.backend.controller.CommentController;
import com.backend.persistence.entity.CommentEntity;
import com.backend.service.CommentService;
import com.backend.persistence.inputDTO.CommentInputDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;

    @Override
    @GetMapping("/{id}")
    public CommentEntity findCommentById(@PathVariable Long id) {
        return commentService.findCommentById(id);
    }

    @Override
    @GetMapping("/post/{id}")
    public List<CommentInputDTO> findCommentsOfAPost(@PathVariable Long id){
        return commentService.findCommentsOfAPost(id);
    }
}

