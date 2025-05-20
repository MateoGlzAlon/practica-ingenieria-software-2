package com.backend.service.impl;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.inputDTO.PostVoteInputDTO;
import com.backend.repository.PostRepository;
import com.backend.repository.PostVoteRepository;
import com.backend.repository.UserRepository;
import com.backend.service.PostVoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;

@Service
@AllArgsConstructor
public class PostVoteServiceImpl implements PostVoteService {

    private final PostVoteRepository postVoteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public PostVoteEntity findPostVoteById(Long id) {
        return postVoteRepository.findById(id).orElse(null);
    }

    @Override
    public PostVoteEntity createPostVote(PostVoteInputDTO postVote) {

        PostVoteEntity postVoteEntity = PostVoteEntity.builder()
                .user(userRepository.findById(postVote.getUserId()).orElse(null))
                .post(postRepository.findById(postVote.getPostId()).orElse(null))
                .build();

        PostEntity postEntity = postRepository.findById(postVote.getPostId()).orElse(null);

        postEntity.setVotes(postEntity.getVotes() + 1);
        postRepository.save(postEntity);

        return postVoteRepository.save(postVoteEntity);
    }
}
