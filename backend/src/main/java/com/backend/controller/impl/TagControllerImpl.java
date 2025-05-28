package com.backend.controller.impl;

import com.backend.controller.TagController;
import com.backend.persistence.entity.TagEntity;
import com.backend.service.TagService;
import lombok.AllArgsConstructor;
import com.backend.persistence.outputdto.TagOutputDTO;

import org.springframework.web.bind.annotation.*;


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

    @Override
    @GetMapping("/all")
    public TagOutputDTO findTags(){
        return tagService.findTags();
    }

}
