package com.backend.controller.impl;

import com.backend.controller.PostImageController;
import com.backend.persistence.entity.PostImageEntity;
import com.backend.service.PostImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post-images") // change in case of confusion
@AllArgsConstructor
public class PostImageControllerImpl implements PostImageController {

    private final PostImageService postImageService;

    @Override
    @GetMapping("/{id}")
    public PostImageEntity findPostImageById(@PathVariable Long id) {
        return postImageService.findPostImageById(id);
    }

}
