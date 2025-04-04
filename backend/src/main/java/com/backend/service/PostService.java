package com.backend.service;

import com.backend.persistence.entity.PostEntity;

public interface PostService {

    PostEntity findPostById(Long id);

}


