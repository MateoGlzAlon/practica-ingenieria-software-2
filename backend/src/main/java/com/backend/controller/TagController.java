package com.backend.controller;

import com.backend.persistence.entity.TagEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tags")
public interface TagController {

    @GetMapping("/{id}")
    TagEntity findTagById(@PathVariable Long id);

}