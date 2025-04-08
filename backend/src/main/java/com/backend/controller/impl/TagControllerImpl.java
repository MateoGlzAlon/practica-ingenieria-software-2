package com.backend.controller.impl;

import com.backend.controller.TagController;
import com.backend.persistence.entity.TagEntity;
import com.backend.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagService tagService;

    @Override
    @GetMapping("/{id}")
    public TagEntity findTagById(@PathVariable Long id) {

        TagEntity tag = tagService.findTagById(id);
        return tag;
    }

}
