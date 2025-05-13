package com.backend.service;

import com.backend.persistence.entity.TagEntity;
import com.backend.persistence.outputdto.TagOutputDTO;

public interface TagService {

    TagEntity findTagById(Long id);

    TagOutputDTO findTags();

}
