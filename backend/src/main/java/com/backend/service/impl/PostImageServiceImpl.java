package com.backend.service.impl;

import com.backend.persistence.entity.PostImageEntity;
import com.backend.repository.PostImageRepository;
import com.backend.service.PostImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostImageServiceImpl implements PostImageService {

    private final PostImageRepository postImageRepository;


    @Override
    public PostImageEntity findPostImageById(Long id) {
        return postImageRepository.findById(id).orElse(null);
    }

}
