package com.backend.controller;

import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.TagCreatePostDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/tags")
public interface TagController {

    @GetMapping("/{id}")
    TagEntity findTagById(@PathVariable Long id);

    @GetMapping("/all")
    TagOutputDTO findTags();

    @GetMapping("/available")
    List<TagCreatePostDTO> getTagsAvailablePost();

}