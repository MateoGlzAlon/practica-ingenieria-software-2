package com.backend.service;

import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.inputDTO.PostVoteInputDTO;

public interface PostVoteService {
    PostVoteEntity findPostVoteById(Long id);

    PostVoteEntity createPostVote(PostVoteInputDTO postVote);

    boolean isPostVoted(Long userId, Long postId);
}
