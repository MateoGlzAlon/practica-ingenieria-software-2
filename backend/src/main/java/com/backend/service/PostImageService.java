package com.backend.service;

import com.backend.persistence.entity.PostImageEntity;

public interface PostImageService {

    PostImageEntity findPostImageById(Long id);

}
