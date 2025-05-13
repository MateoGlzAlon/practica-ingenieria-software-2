package com.backend.service.impl;

import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.outputdto.TagOutputDTO;
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
    public TagOutputDTO findTags(){
        List<TagEntity> tags = tagRepository.findAll();

        List<String> listNameTags = new ArrayList<>();

        for(TagEntity newTag : tags){
            listNameTags.add(newTag.getName());
        }

        TagOutputDTO tagOutputDTO = new TagOutputDTO();
        tagOutputDTO.setTags(listNameTags);

        return tagOutputDTO;

    }

}