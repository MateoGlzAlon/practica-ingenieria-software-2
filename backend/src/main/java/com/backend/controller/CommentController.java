package com.backend.controller;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.inputDTO.CommentInputDTO;
import com.backend.persistence.outputdto.CommentOutputDTO;
import com.backend.persistence.inputDTO.CommentAcceptDTO;
import com.backend.persistence.outputdto.UserCommentDTO;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comments")
public interface CommentController {

    @GetMapping("/{id}")
    CommentEntity findCommentById(@PathVariable Long id);

    @GetMapping("/post/{id}")
    List<CommentOutputDTO> findCommentsOfAPost(@PathVariable Long id, @RequestParam(required = false, defaultValue = "newest") String sort);

    @PostMapping
    CommentEntity createComment(@RequestBody CommentInputDTO comment);

    @PostMapping("/accept")
    CommentEntity acceptComment(@RequestBody CommentAcceptDTO comment);

    @GetMapping("/user/{idUser}")
    List<UserCommentDTO> getCommentsOfAUser(@PathVariable Long idUser);

    @DeleteMapping("/{id}")
    void deleteComment(@PathVariable Long id);
}
