package com.backend.controller;

import com.backend.persistence.entity.PostImageEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/post-images")
public interface PostImageController {

    @GetMapping("/{id}")
    PostImageEntity findPostImageById(@PathVariable Long id);

}