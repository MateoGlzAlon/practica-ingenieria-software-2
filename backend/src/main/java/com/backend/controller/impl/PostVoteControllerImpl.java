package com.backend.controller.impl;

import com.backend.controller.PostVoteController;
import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.inputDTO.PostVoteInputDTO;
import com.backend.service.PostVoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postvotes")
@AllArgsConstructor
public class PostVoteControllerImpl implements PostVoteController {

    private final PostVoteService postVoteService;

    @Override
    @GetMapping("/{id}")
    public PostVoteEntity findPostVoteById(@PathVariable Long id){
        return postVoteService.findPostVoteById(id);
    }

    @Override
    @PostMapping
    public PostVoteEntity createPostVote(@RequestBody PostVoteInputDTO postVote) {
        return postVoteService.createPostVote(postVote);
    }
}
