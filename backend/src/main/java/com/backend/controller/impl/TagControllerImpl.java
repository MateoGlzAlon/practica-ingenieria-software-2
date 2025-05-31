package com.backend.controller.impl;

import com.backend.controller.TagController;
import com.backend.persistence.entity.TagEntity;
import com.backend.service.TagService;
import lombok.AllArgsConstructor;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.TagCreatePostDTO;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagService tagService;

    @Override
    @GetMapping("/{id}")
    public TagEntity findTagById(@PathVariable Long id) {
        return tagService.findTagById(id);
    }

    @Override
    @GetMapping("/all")
    public TagOutputDTO findTags(){
        return tagService.findTags();
    }

    @Override
    @GetMapping("/available")
    public List<TagCreatePostDTO> getTagsAvailablePost(){
        return tagService.getTagsAvailablePost();
    }

}
