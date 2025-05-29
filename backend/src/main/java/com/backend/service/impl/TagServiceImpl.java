package com.backend.service.impl;

import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.repository.TagRepository;
import com.backend.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;


@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;


    @Override
    public TagEntity findTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }


    @Override
    public TagOutputDTO findTags(){

        List<TagEntity> tags = tagRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        List<String> tagsString = new ArrayList<>();

        for (TagEntity tag : tags) {
            tagsString.add(tag.getName());
        }

        return TagOutputDTO.builder().tags(tagsString).build();

    }

}