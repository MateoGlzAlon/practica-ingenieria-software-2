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
    public TagOutputDTO findTags(int page, int size){

        Pageable pageable = PageRequest.of(page, size);
        Page<TagEntity> tagPage = tagRepository.findAllRandom(pageable);

        List<String> listNameTags = new ArrayList<>();

        for(TagEntity newTag : tagPage.getContent()){
            listNameTags.add(newTag.getName());
        }

        TagOutputDTO tagOutputDTO = new TagOutputDTO();
        tagOutputDTO.setTags(listNameTags);

        return tagOutputDTO;

    }

}