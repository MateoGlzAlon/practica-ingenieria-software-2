package com.backend.controller.impl;

import com.backend.controller.PostVoteController;
import com.backend.persistence.entity.PostVoteEntity;
import com.backend.service.PostVoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postvotes")
@AllArgsConstructor
public class PostVoteControllerImpl implements PostVoteController {

    private final PostVoteService postVoteService;

    @GetMapping("/{id}")
    public PostVoteEntity findPostById(@PathVariable Long id){
        return postVoteService.findPostById(id);
    }
}
