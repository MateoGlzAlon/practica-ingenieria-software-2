package com.backend.service;

import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.specialdto.TagsSearchDTO;

public interface TagService {

    TagEntity findTagById(Long id);

    TagsSearchDTO findTags();

}
