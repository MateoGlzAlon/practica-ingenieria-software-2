package com.backend.service;

import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.outputdto.TagOutputDTO;
import com.backend.persistence.outputdto.TagCreatePostDTO;

import java.util.List;

public interface TagService {

    TagEntity findTagById(Long id);

    TagOutputDTO findTags();

    List<TagCreatePostDTO> getTagsAvailablePost();

}
