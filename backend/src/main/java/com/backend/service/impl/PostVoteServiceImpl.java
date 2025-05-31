package com.backend.service.impl;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.PostVoteEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.PostVoteInputDTO;
import com.backend.repository.*;
import com.backend.service.PostVoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

    import java.util.Optional;

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

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        Optional<PostEntity> postOpt = postRepository.findById(postId);

        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("Post not found with ID: " + postId);
        }

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        PostEntity post = postOpt.get();
        UserEntity user = userOpt.get();

        boolean hasVoted = postVoteRepository.isPostVoted(userId, postId);

        if (hasVoted) {
            postVoteRepository.deleteByUserIDProjectId(userId, postId);
            post.decreaseVotes();
            postRepository.save(post);
            return null; // voto eliminado
        }

        post.increaseVotes();
        postRepository.save(post);

        PostVoteEntity postVoteEntity = PostVoteEntity.builder()
                .user(user)
                .post(post)
                .build();

        return postVoteRepository.save(postVoteEntity);
    }

    @Override
    public boolean isPostVoted(Long userId, Long postId) {
        return postVoteRepository.isPostVoted(userId, postId);
    }

}
