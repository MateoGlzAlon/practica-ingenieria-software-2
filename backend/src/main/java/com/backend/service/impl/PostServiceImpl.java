package com.backend.service.impl;

import com.backend.persistence.entity.PostEntity;
import com.backend.repository.PostRepository;
import com.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    @Override
    public PostEntity findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

}
