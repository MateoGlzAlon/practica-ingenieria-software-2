package com.backend.service.impl;

import com.backend.exception.UserNotFoundException;
import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.CommentVoteEntity;
import com.backend.persistence.inputDTO.CommentVoteInputDTO;
import com.backend.repository.CommentVoteRepository;
import com.backend.repository.UserRepository;
import com.backend.repository.CommentRepository;
import com.backend.service.CommentVoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentVoteServiceImpl implements CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentVoteEntity findCommentVoteById(Long id) {
        return commentVoteRepository.findById(id).orElse(null);
    }

    @Override
    public CommentVoteEntity createCommentVote(CommentVoteInputDTO commentVote) {
        Long userId = commentVote.getUserId();
        Long commentId = commentVote.getCommentId();

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        Optional<CommentEntity> commentOpt = commentRepository.findById(commentId);

        if (commentOpt.isEmpty()) {
            throw new IllegalArgumentException("Comment not found with ID: " + commentId);
        }

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        CommentEntity comment = commentOpt.get();
        UserEntity user = userOpt.get();

        if (commentVoteRepository.isCommentVoted(userId, commentId)) {
            commentVoteRepository.deleteByUserIDProjectId(userId, commentId);
            comment.decreaseVotes();
            commentRepository.save(comment);
            return null; // voto removido
        }

        comment.increaseVotes();
        commentRepository.save(comment);

        CommentVoteEntity commentVoteEntity = CommentVoteEntity.builder()
                .user(user)
                .comment(comment)
                .build();

        return commentVoteRepository.save(commentVoteEntity);
    }


    @Override
    public boolean isCommentVoted(Long userId,Long commentId){
        if(userId == null) return false;

        return commentVoteRepository.isCommentVoted(userId, commentId);
    }
}
