package com.backend.controller.impl;

import com.backend.controller.CommentController;
import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.service.CommentService;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.inputDTO.CommentAcceptDTO;
import com.backend.persistence.outputdto.UserCommentDTO;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

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
    public List<CommentOutputDTO> findCommentsOfAPost(@PathVariable Long id, @RequestParam(required = false, defaultValue = "votes") String sort) {
        return commentService.findCommentsOfAPost(id, sort);
    }


    @Override
    @PostMapping
    public CommentEntity createComment(@RequestBody CommentInputDTO comment){
        return commentService.createComment(comment);
    }

    @Override
    @PostMapping("/accept")
    public CommentEntity acceptComment(@RequestBody CommentAcceptDTO comment){
        return commentService.acceptComment(comment);
    }

    @Override
    @GetMapping("/user/{idUser}")
    public List<UserCommentDTO> getCommentsOfAUser(@PathVariable Long idUser){
        return commentService.getCommentsOfAUser(idUser);
    }

}

