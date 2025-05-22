package com.backend.controller;

import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.inputDTO.PostVoteInputDTO;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/postvotes")
public interface PostVoteController {
    PostVoteEntity findPostVoteById(Long id);

    @PostMapping
    PostVoteEntity createPostVote(PostVoteInputDTO postVote);

    @GetMapping("/check")
    boolean isPostVoted(@RequestParam Long userId, @RequestParam Long postId);
}
