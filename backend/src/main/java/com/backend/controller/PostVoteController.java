package com.backend.controller;

import com.backend.persistence.entity.PostVoteEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/postvotes")
public interface PostVoteController {
    PostVoteEntity findPostById(Long id); 
}
