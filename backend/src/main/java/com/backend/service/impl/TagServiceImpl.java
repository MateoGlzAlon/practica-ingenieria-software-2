package com.backend.service.impl;

import com.backend.persistence.specialdto.TagsSearchDTO;
import com.backend.persistence.entity.TagEntity;
import com.backend.repository.TagRepository;
import com.backend.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;


    @Override
    public TagEntity findTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public TagsSearchDTO findTags(){
        List<TagEntity> tags = tagRepository.findAll();

        List<String> listNameTags = new ArrayList<>();

        for(TagEntity newTag : tags){
            listNameTags.add(newTag.getName());
        }

        TagsSearchDTO tagsSearchDTO = new TagsSearchDTO();
        tagsSearchDTO.setTags(listNameTags);

        return tagsSearchDTO;

    }

}