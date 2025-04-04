package com.backend.controller;

import com.backend.persistence.entity.PostEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/posts")
public interface PostController {

    @GetMapping("/{id}")
    PostEntity findPostById(@PathVariable Long id);

}
