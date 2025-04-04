package com.backend.controller.impl;

import com.backend.controller.PostController;
import com.backend.persistence.entity.PostEntity;
import com.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;

    @Override
    @GetMapping("/{id}")
    public PostEntity findPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }

}

