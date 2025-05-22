package com.backend.controller;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comments")
public interface CommentController {

    @GetMapping("/{id}")
    CommentEntity findCommentById(@PathVariable Long id);

    @GetMapping("/post/{id}")
    List<CommentOutputDTO> findCommentsOfAPost(@PathVariable Long id);


    @PostMapping
    CommentEntity createComment(@RequestBody CommentInputDTO comment);

}
