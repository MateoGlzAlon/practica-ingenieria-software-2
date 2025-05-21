package com.backend.service.impl;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.PostVoteInputDTO;
import com.backend.repository.*;
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
    private final CommentRepository commentRepository;


    @Override
    public PostVoteEntity findPostVoteById(Long id) {
        return postVoteRepository.findById(id).orElse(null);
    }

    @Override
    public PostVoteEntity createPostVote(PostVoteInputDTO postVote) {
        Long userId = postVote.getUserId();
        Long postId = postVote.getPostId();

        boolean hasVoted = postVoteRepository.isPostVoted(userId, postId);

        UserEntity user = userRepository.findById(userId).orElse(null);
        PostEntity post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            // TODO: Consider throwing an exception or handling the null case
            return null;
        }

        if (hasVoted) {
            postVoteRepository.deleteByUserIDProjectId(userId, postId);
            post.decreaseVotes();
            postRepository.save(post);
            return null;
        } else {
            post.increaseVotes();
            postRepository.save(post);

            PostVoteEntity postVoteEntity = PostVoteEntity.builder()
                    .user(user)
                    .post(post)
                    .build();

            return postVoteRepository.save(postVoteEntity);
        }
    }

    @Override
    public boolean isPostVoted(Long userId, Long postId) {
        return postVoteRepository.isPostVoted(userId, postId);
    }

}
