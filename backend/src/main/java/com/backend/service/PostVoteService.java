package com.backend.service;

import com.backend.persistence.entity.PostVoteEntity;

public interface PostVoteService {
    PostVoteEntity findPostById(Long id);
}
