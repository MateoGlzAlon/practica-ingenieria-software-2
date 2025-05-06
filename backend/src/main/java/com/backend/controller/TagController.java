package com.backend.controller;

import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.specialdto.TagsSearchDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tags")
public interface TagController {

    @GetMapping("/{id}")
    TagEntity findTagById(@PathVariable Long id);

    @GetMapping("/all")
    TagsSearchDTO findTags();

}