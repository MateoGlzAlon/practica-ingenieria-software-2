package com.backend.service.impl;

import com.backend.persistence.entity.PostVoteEntity;
import com.backend.repository.PostVoteRepository;
import com.backend.service.PostVoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostVoteServiceImpl implements PostVoteService {

    private final PostVoteRepository postVoteRepository;

    @Override
    public PostVoteEntity findPostById(Long id) {
        return postVoteRepository.findById(id).orElse(null);
    }
}
