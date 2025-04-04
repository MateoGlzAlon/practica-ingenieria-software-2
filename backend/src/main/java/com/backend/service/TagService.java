package com.backend.service;

import com.backend.persistence.entity.TagEntity;

public interface TagService {

    TagEntity findTagById(Long id);

}
